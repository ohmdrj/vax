package cz.req.ax;

import com.vaadin.ui.UI;
import ru.xpoft.vaadin.VaadinView;

import java.lang.annotation.Annotation;

/**
 * @author by Ondřej Buriánek, burianek@marbes.cz.
 * @since 13.1.15
 */
@FunctionalInterface
public interface Navigation {

    String string = null;

    @Deprecated
    public Object getNavigationParameter();

    default public String viewName(Class<? extends AxView> viewClass) {
        Annotation annotation = viewClass.getAnnotation(VaadinView.class);
        return ((VaadinView) annotation).value();

    }

    default public AxAction<String> action(Class<? extends AxView> viewClass) {
        String viewName = viewName(viewClass);
        return new AxAction<String>().caption(viewName).value(viewName).action(this::navigate);
    }

    default public void navigate(String viewName) {
        navigate(viewName, getNavigationParameter());
    }

    default public void navigate(Class<? extends AxView> viewClass, Object parameter) {
        navigate(viewName(viewClass), parameter);
    }

    default public void navigate(String viewName, Object parameter) {
        StringBuffer viewState = new StringBuffer(viewName);
        if (parameter != null) {
            viewState.append("/").append(parameter.toString());
        }
        UI.getCurrent().getNavigator().navigateTo(viewState.toString());
    }
}
