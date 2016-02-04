package cz.req.ax;

import com.vaadin.ui.JavaScript;
import com.vaadin.ui.UI;
import ru.xpoft.vaadin.VaadinView;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * @author by Ondřej Buriánek, burianek@marbes.cz.
 * @since 13.1.15
 */
@FunctionalInterface
public interface Navigation {

    String string = null;

    String getId();

    default AxAction<String> action(Class<? extends AxView> viewClass) {
        String viewName = viewName(viewClass);
        return new AxAction<String>().caption(viewName).value(viewName).action(this::navigate);
    }

    default AxAction<String> action(Class<? extends AxView> viewClass, String userRole) {
        RequireRole annotation = viewClass.getAnnotation(RequireRole.class);
        AxAction<String> action = action(viewClass);
        if (annotation != null && annotation.value() != null && annotation.value().length > 0) {
            action.disabled();
            if (Arrays.asList(annotation.value()).contains(userRole)) {
                action.enabled();
            }
        }
        return action;
    }

    default void navigate(Class<? extends AxView> viewClass) {
        navigate(viewName(viewClass), null);
    }

    default void navigate(Class<? extends AxView> viewClass, Object... parameter) {
        navigate(viewName(viewClass), parameter);
    }

    default void navigate(String viewName) {
        navigate(viewName, null);
    }

    default void navigate(String viewName, Object... parameters) {
        String viewState = AxUtils.makeURL(viewName, parameters);
        UI.getCurrent().getNavigator().navigateTo(viewState);
    }

    default void navigateBack() {
        JavaScript.eval("window.history.back();");
    }

    default String viewName(Class<? extends AxView> viewClass) {
        Annotation annotation = viewClass.getAnnotation(VaadinView.class);
        if (annotation == null)
            throw new RuntimeException("View class " + viewClass + " missing annotation VaadinView");
        return ((VaadinView) annotation).value();

    }

}
