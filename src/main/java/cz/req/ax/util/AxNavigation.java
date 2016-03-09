package cz.req.ax.util;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.UI;
import cz.req.ax.AxUtils;
import org.springframework.util.Assert;
import ru.xpoft.vaadin.VaadinView;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 18.2.2016
 */
public class AxNavigation {

    public static AxNavigation getCurrent() {
        return new AxNavigation(UI.getCurrent().getNavigator());
    }

    private Navigator navigator;

    public AxNavigation(Navigator navigator) {
        this.navigator = navigator;
    }

    public void to(Class<? extends View> viewClass, Object... params) {
        VaadinView annotation = viewClass.getAnnotation(VaadinView.class);
        Assert.notNull(annotation, "No VaadinView annotation on class " + viewClass);
        to(annotation.value(), params);
    }

    public void to(String viewName, Object... params) {
        navigator.navigateTo(AxUtils.makeURL(viewName, params));
    }

    public void forward() {
        JavaScript.eval("window.forward.back();");
    }

    public void back() {
        JavaScript.eval("window.history.back();");
    }

}
