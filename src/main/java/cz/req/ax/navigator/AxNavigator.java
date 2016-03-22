package cz.req.ax.navigator;

import com.google.common.base.Strings;
import com.vaadin.navigator.View;
import com.vaadin.ui.JavaScript;
import cz.req.ax.AxProperties;
import cz.req.ax.AxUtils;
import cz.req.ax.ui.AxUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.xpoft.vaadin.DiscoveryNavigator;
import ru.xpoft.vaadin.VaadinView;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 22.3.2016
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AxNavigator extends DiscoveryNavigator {

    public static AxNavigator getCurrent() {
        return AxUI.getCurrent().getNavigator();
    }

    @Autowired
    private AxProperties properties;

    public AxNavigator(AxUI ui) {
        super(ui, ui);
        addViewChangeListener(ui);
    }

    public void navigateToInitialView() {
        if (!navigateToRequestedView() && !navigateToUserView()) {
            navigateToMainView();
        }
    }

    private boolean navigateToRequestedView() {
        String requestedView = getState();
        if (Strings.isNullOrEmpty(requestedView)) {
            return false;
        }
        navigateTo(requestedView);
        return true;
    }

    public boolean navigateToUserView() {
        if (!canNavigateToUserView()) {
            return false;
        }
        String userView = properties.getViewUser();
        if (Strings.isNullOrEmpty(userView)) {
            return false;
        }
        navigateTo(userView);
        return true;
    }

    protected boolean canNavigateToUserView() {
        return false; // Authentication
    }

    public void navigateToMainView() {
        String mainView = properties.getViewMain();
        if (Strings.isNullOrEmpty(mainView)) {
            throw new IllegalArgumentException("Missing default view configuration property vax.viewMain");
        }
        navigateTo(mainView);
    }

    public boolean navigateToErrorView(Throwable throwable) {
        String errorView = properties.getViewError();
        if (Strings.isNullOrEmpty(errorView)) {
            return false;
        }
        getUI().getSession().setAttribute(Throwable.class, throwable);
        navigateTo(errorView);
        return true;
    }

    public void navigateTo(Class<? extends View> viewClass, Object... params) {
        VaadinView annotation = viewClass.getAnnotation(VaadinView.class);
        Assert.notNull(annotation, "No VaadinView annotation on class " + viewClass);
        navigateTo(annotation.value(), params);
    }

    public void navigateTo(String viewName, Object... params) {
        navigateTo(AxUtils.makeURL(viewName, params));
    }

    public void navigateForward() {
        JavaScript.eval("window.forward.back();");
    }

    public void navigateBack() {
        JavaScript.eval("window.history.back();");
    }

}
