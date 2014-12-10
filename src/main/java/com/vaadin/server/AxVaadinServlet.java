package com.vaadin.server;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.vaadin.sass.internal.ScssStylesheet;
import com.vaadin.util.CurrentInstance;
import ru.xpoft.vaadin.SpringVaadinServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class AxVaadinServlet extends SpringVaadinServlet {

    File resourcesDir;
    Semaphore semaphore = new Semaphore(1);
    Map<String, CssCacheItem> cache = new ConcurrentHashMap<>();
    LoadingCache<String, CssCacheItem> graphs = CacheBuilder.newBuilder().build(
            new CacheLoader<String, CssCacheItem>() {
                @Override
                public CssCacheItem load(String key) throws Exception {
                    return null;
                }
            }
    );


    public AxVaadinServlet(File resourcesDir) {
        if (!resourcesDir.exists())
            throw new RuntimeException("Missing resources directory " + resourcesDir);
        this.resourcesDir = resourcesDir;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // SASS compile on fly
        if (request.getServletPath().equals("/VAADIN")
                && request.getPathInfo().startsWith("/themes")
                && request.getPathInfo().endsWith(".css")) {
            CurrentInstance.clearAll();
            VaadinServletRequest vaadinRequest = new VaadinServletRequest(request, getService());
            VaadinServletResponse vaadinResponse = new VaadinServletResponse(response, getService());
            getService().setCurrentInstances(null, null);
            try {
                semaphore.acquire();
                String scssName = request.getPathInfo().substring(0, request.getPathInfo().length() - 4);
                CssCacheItem cacheItem = cache.get(scssName);
                if (cacheItem == null) {
                    cacheItem = new CssCacheItem(new File(resourcesDir, "VAADIN" + scssName + ".scss"));
                    cache.put(scssName, cacheItem);
                }

                response.setHeader("Cache-Control", "no-cache");
                PrintWriter writer = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(response.getOutputStream(), "UTF-8")));
                writer.print(cacheItem.getContent());
                writer.flush();
                writer.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                CurrentInstance.clearAll();
                semaphore.release();
            }
            try {
                getService().handleRequest(vaadinRequest, vaadinResponse);
            } catch (ServiceException e) {
                throw new ServletException(e);
            }
        } else {
            super.service(request, response);
        }
    }


    public static class CssCacheItem {
        private Long time;
        private File file;
        private String content;

        public CssCacheItem(File file) {
            if (!file.exists()) throw new RuntimeException("Missing scss style file " + file);
            this.file = file;
        }

        public String getContent() {
            if (content == null || file.lastModified() > time) {
                loadContent();
            }
            return content;
        }

        protected void loadContent() {
            try {
                ScssStylesheet scssStyle = ScssStylesheet.get(file.getAbsolutePath());
                scssStyle.compile();
                content = scssStyle.printState();
                time = System.currentTimeMillis();
            } catch (Exception e) {
                throw new RuntimeException("Error sass style compilation", e);
            }
        }
    }
}
