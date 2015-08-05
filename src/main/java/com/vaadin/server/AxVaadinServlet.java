package com.vaadin.server;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.io.ByteStreams;
import com.vaadin.sass.internal.ScssStylesheet;
import com.vaadin.util.CurrentInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.xpoft.vaadin.SpringVaadinServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class AxVaadinServlet extends SpringVaadinServlet {

    static final Logger logger = LoggerFactory.getLogger(AxVaadinServlet.class);
    File themeDir;
    boolean themeCompile = false;
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

    public AxVaadinServlet() {
        setThemeDir(new File(System.getProperty("user.dir") + "/src/main/resources"));
    }

    public void setThemeDir(File resourcesDir) {
        this.themeDir = resourcesDir;
        themeCompile = resourcesDir != null && resourcesDir.exists();
        if (themeCompile) {
            logger.info("Dynamic SASS compile resources directory: " + resourcesDir);
        } else {
            logger.info("Dynamic SASS compile missing resources directory: " + resourcesDir);
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder builder = new StringBuilder();
        if (request.getServletPath()!=null) builder.append(request.getServletPath());
        if (request.getPathInfo()!=null) builder.append(request.getPathInfo());
        String path = builder.toString();
        if (path.startsWith("/APP/PUBLISHED")) {
            response.setHeader("Cache-Control", "no-cache");
            try {
                InputStream stream = getClass().getResourceAsStream(request.getPathInfo());
                ByteStreams.copy(stream, response.getOutputStream());
            } catch (Exception e) {
                log("Error serving static resource " + path, e);
            }
        } else if (themeCompile && path.contains("/themes/") && path.endsWith("styles.css")) {
            CurrentInstance.clearAll();
            VaadinServletRequest vaadinRequest = new VaadinServletRequest(request, getService());
            VaadinServletResponse vaadinResponse = new VaadinServletResponse(response, getService());
            getService().setCurrentInstances(null, null);
            try {
                semaphore.acquire();
                String scssName = path.substring(0, path.length() - 4);
                CssCacheItem cacheItem = cache.get(scssName);
                if (cacheItem == null) {
                    cacheItem = new CssCacheItem(themeDir, scssName + ".scss");
                    cache.put(scssName, cacheItem);
                }
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/css;charset=UTF-8");
                response.setHeader("Cache-Control", "no-cache");
                PrintWriter writer = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(response.getOutputStream(), "UTF-8")));
                writer.print(cacheItem.getContent());
                writer.flush();
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
        private List<File> imports;

        public CssCacheItem(File themeDir, String sassPath) {
            logger.warn("Watch sass changes in " + themeDir);
            file = new File(themeDir, sassPath);
            if (!file.exists()) throw new RuntimeException("Missing theme file " + file);
            imports = new ArrayList<>();
            try {
                Files.walkFileTree(file.getParentFile().toPath(), new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if (file.toString().endsWith(".scss")) {
                            logger.warn("   - " + file);
                            imports.add(file.toFile());
                        }
                        return super.visitFile(file, attrs);
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException("Error reading tree", e);
            }
        }

        public String getContent() {
            if (time == null) return loadContent();
            boolean modified = false;
            if (time != null) for (File file : imports) {
                long lastm = file.lastModified();
                if (file != null && lastm > time) {
                    logger.info("Modified sass file " + file);
                    modified = true;
                }
            }
            if (modified) return loadContent();
            return content;
        }

        protected String loadContent() {
            try {
                ScssStylesheet scssStyle = ScssStylesheet.get(file.getAbsolutePath());
                scssStyle.compile();
                content = scssStyle.printState();
                time = System.currentTimeMillis();
                return content;
            } catch (Exception e) {
                throw new RuntimeException("Error sass style compilation", e);
            }
        }
    }
}
