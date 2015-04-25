package cz.req.ax;

import com.google.common.eventbus.EventBus;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import ru.xpoft.vaadin.DiscoveryNavigator;

import java.lang.reflect.Field;

public abstract class AxUI extends UI implements ViewChangeListener {

    @Value("${vax.viewMain}")
    String mainView;
    @Value("${vax.viewError}")
    String errorView;
    @Value("${debug.firstView}")
    String debugView;

    @Autowired
    EventBus eventBus;

    public AxUI() {
        setSizeUndefined();
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    private ErrorHandler errorHandler = new DefaultErrorHandler() {
        @Override
        public void error(com.vaadin.server.ErrorEvent event) {
            super.error(event);
            UI.getCurrent().getSession().setAttribute(Throwable.class, event.getThrowable());
            UI.getCurrent().getNavigator().navigateTo(errorView);
        }
    };

    @Override
    protected void init(VaadinRequest request) {
        setErrorHandler(errorHandler);
        DiscoveryNavigator navigator = new DiscoveryNavigator(this, this);
        navigator.addViewChangeListener(this);
        setNavigator(navigator);
        try {
            if (StringUtils.isEmpty(navigator.getState())) {
                navigate();
            }
        } catch (Throwable th) {
            getSession().setAttribute(Throwable.class, th);
            getNavigator().navigateTo(errorView);
        }
    }

    protected void navigate() {
        if (StringUtils.isEmpty(debugView)) {
            Assert.notNull(mainView);
            getNavigator().navigateTo(mainView);
        } else {
            getNavigator().navigateTo(debugView);
        }
    }

    @Override
    public void attach() {
        eventBus.register(this);
        super.attach();
    }

    @Override
    public void detach() {
        eventBus.unregister(this);
        try {
            Field field = Navigator.class.getDeclaredField("currentView");
            field.setAccessible(true);
            Object view = field.get(getNavigator());
            eventBus.unregister(view);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error unregister view " + e.getMessage());
        }
        super.detach();
    }

    @Override
    public boolean beforeViewChange(ViewChangeEvent event) {
        return true;
    }

    @Override
    public void afterViewChange(ViewChangeEvent event) {
        if (event.getOldView() != null) eventBus.unregister(event.getOldView());
        if (event.getNewView() != null) eventBus.register(event.getNewView());
    }
}
