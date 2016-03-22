package cz.req.ax.ui;

import com.google.common.eventbus.EventBus;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import cz.req.ax.server.AxErrorHandler;
import cz.req.ax.navigator.AxNavigator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

public abstract class AxUI extends UI implements ViewChangeListener {

    private static final Logger logger = LoggerFactory.getLogger(AxUI.class);

    public static AxUI getCurrent() {
        UI ui = UI.getCurrent();
        Assert.isTrue(ui instanceof AxUI, "Current UI is not instance of AxUI");
        return (AxUI) ui;
    }

    @Autowired
    private ApplicationContext context;
    @Autowired
    private EventBus eventBus;

    private View currentView;

    public AxUI() {
        setSizeUndefined();
    }

    @Override
    public void doInit(VaadinRequest request, int uiId, String embedId) {
        super.doInit(request, uiId, embedId);
        initNavigator(); // Záměrně mimo doInit, abychom si mohli výchozí navigaci pořešit sami
        navigateToInitialView();
    }

    @Override
    protected void init(VaadinRequest request) {
        initErrorHandler();
    }

    protected void initErrorHandler() {
        setErrorHandler(new AxErrorHandler());
    }

    protected void initNavigator() {
        setNavigator(context.getBean(AxNavigator.class, this));
    }

    protected void navigateToInitialView() {
        try {
            getNavigator().navigateToInitialView();
        } catch (Throwable throwable) {
            getErrorHandler().handleThrowable(throwable);
        }
    }

    @Override
    public void setErrorHandler(ErrorHandler errorHandler) {
        Assert.isTrue(errorHandler instanceof AxErrorHandler, "Error handler is not instance of AxErrorHandler");
        super.setErrorHandler(errorHandler);
    }

    @Override
    public void setNavigator(Navigator navigator) {
        Assert.isTrue(navigator instanceof AxNavigator, "Navigator is not instance of AxNavigator");
        super.setNavigator(navigator);
    }

    @Override
    public AxErrorHandler getErrorHandler() {
        return (AxErrorHandler) super.getErrorHandler();
    }

    @Override
    public AxNavigator getNavigator() {
        return (AxNavigator) super.getNavigator();
    }

    @Override
    public void attach() {
        super.attach();
        eventBus.register(this);
    }

    @Override
    public void detach() {
        getWindows().forEach(this::unregisterWindow);
        unregisterCurrentView();
        eventBus.unregister(this);
        super.detach();
    }

    @Override
    public boolean beforeViewChange(ViewChangeEvent event) {
        return true;
    }

    @Override
    public void afterViewChange(ViewChangeEvent event) {
        unregisterCurrentView();
        currentView = event.getNewView();
        registerCurrentView();
    }

    private void unregisterCurrentView() {
        if (currentView != null) {
            unregisterOnEventBus(currentView);
        }
    }

    private void registerCurrentView() {
        if (currentView != null) {
            registerOnEventBus(currentView);
        }
    }

    @Override
    public void addWindow(Window window) {
        super.addWindow(window);
        registerWindow(window);
    }

    private void registerWindow(Window window) {
        Component content = window.getContent();
        if (content != null) {
            registerOnEventBus(content);
            window.addCloseListener(e -> unregisterOnEventBus(content));
        }
    }

    private void unregisterWindow(Window window) {
        Component content = window.getContent();
        if (content != null) {
            unregisterOnEventBus(content);
        }
    }

    protected void registerOnEventBus(Object target) {
        try {
            eventBus.register(target);
        } catch (Exception e) {
            logger.error("Unable to register eventBus", e);
        }
    }

    protected void unregisterOnEventBus(Object target) {
        try {
            eventBus.unregister(target);
        } catch (Exception e) {
            logger.error("Unable to unregister eventBus", e);
        }
    }

}
