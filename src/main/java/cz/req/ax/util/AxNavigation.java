package cz.req.ax.util;

import com.vaadin.navigator.View;
import cz.req.ax.navigator.AxNavigator;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 18.2.2016
 */
public class AxNavigation {

    private AxNavigator navigator;

    public AxNavigation(AxNavigator navigator) {
        this.navigator = navigator;
    }

    public void to(Class<? extends View> viewClass, Object... params) {
        navigator.navigateTo(viewClass, params);
    }

    public void to(String viewName, Object... params) {
        navigator.navigateTo(viewName, params);
    }

    public void main() {
        navigator.navigateToMainView();
    }

    public void forward() {
        navigator.navigateForward();
    }

    public void back() {
        navigator.navigateBack();
    }

}
