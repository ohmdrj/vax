package cz.req.ax;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import ru.xpoft.vaadin.VaadinView;

import java.lang.annotation.Annotation;

public class AxMenuBar extends MenuBar {

    public AxMenuBar actions(AxAction... actions) {
        for (AxAction action : actions) {
            if (action != null) action(action);
        }
        return this;
    }

    public AxAction<String> navigate(Class<? extends AxView> viewClass) {
        Annotation annotation = viewClass.getAnnotation(VaadinView.class);
        String viewName = ((VaadinView) annotation).value();
        return new AxAction<String>().caption(viewName).value(viewName).action(this::navigate);
    }

    //TODO Use Navigation??
    public void navigate(String viewState) {
        UI.getCurrent().getNavigator().navigateTo(viewState);
    }

    public MenuItem action(AxAction action) {
        MenuItem item = addItem(action.getCaption(), action.getIcon(), menuItem -> {
            action.onAction();
        });
        if (action.getStyle() != null) item.setStyleName(action.getStyle());
        return item;
    }

    public AxMenuBar menu(String caption, FontAwesome awesome, AxAction... actions) {
        MenuItem menu = addItem(caption, awesome, null);
        for (AxAction action : actions) {
            MenuItem item = menu.addItem(action.getCaption(), action.getIcon(), menuItem -> {
                action.onAction();
            });
            if (action.getStyle() != null) item.setStyleName(action.getStyle());
        }
        return this;
    }


}