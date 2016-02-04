package cz.req.ax;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.ErrorHandler;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class AxWindow extends RootLayout implements Navigation, Components {

    Window window;
    boolean manualFocus = false;

    public AxWindow() {
        setStyleName("window-root");
        window = new Window();
        window.addStyleName("window-headerless");
        window.setSizeUndefined();
        window.setResizable(false);
        window.setClosable(false);
        window.setContent(this);
    }

    public Window getWindow() {
        return window;
    }

    @Override
    public AxMenuBar menuBar() {
        return getVariable(PAGE_MENU, () -> {
            AxMenuBar menuBar = new AxMenuBar();
            menuBar.addStyleName("menu-bar");
            pageFooter().addComponent(menuBar);
            return menuBar;
        });
    }

    //TODO Burianek Review??
    public AxWindow show(UI ui) {
        ui.addWindow(window);
        if (!manualFocus) {
            if (!AxUtils.focusFirst(mainPanel())) {
                focus();
            }
        }
        return this;
    }

    @Override
    protected void focus() {
        window.focus();
    }

    public AxWindow show() {
        return centered().show(UI.getCurrent());
    }

    //TODO Burianek Review
    public AxWindow showTopRight() {
        int x = UI.getCurrent().getPage().getBrowserWindowWidth() - Math.round(window.getWidth()) - 2;
        return show(x, 42);
    }

    public AxWindow show(int x, int y) {
        return position(x, y).show(UI.getCurrent());
    }

    public AxWindow close() {
        window.close();
        return this;
    }

    public AxWindow modal() {
        window.setModal(true);
        return this;
    }

    protected AxWindow centered() {
        window.center();
        return this;
    }

    public AxWindow position(Integer x, Integer y) {
        if (x != null) window.setPositionX(x);
        if (y != null) window.setPositionY(y);
        return this;
    }

    public AxWindow size(Integer width, Integer height) {
        if (width != null) window.setWidth(width, Unit.PIXELS);
        if (height != null) window.setHeight(height, Unit.PIXELS);
        return this;
    }

    public AxWindow style(String styleName) {
        window.addStyleName(styleName);
        return this;
    }

    public AxWindow caption(String caption) {
        pageHeader().addComponent(newLabel(caption, "window-caption"));
        return this;
    }

    public AxWindow components(Component... components) {
        mainComponents(components);
        return this;
    }

    public AxWindow errorHandler(ErrorHandler errorHandler) {
        setErrorHandler(errorHandler);
        return this;
    }

    public AxWindow buttonClose() {
        return buttonClose("Zavřít");
    }

    public AxWindow buttonClose(String caption) {
        AxAction action = new AxAction().caption(caption).run(window::close).right();
        addShortcutListener(action.shortcutListener(ShortcutAction.KeyCode.ESCAPE));
        menuBar().actions(action);
        return this;
    }

    public AxWindow buttonAndClose(AxAction action) {
        Runnable runAfter = action.getRunAfter();
        if (runAfter != null) {
            action.runAfter(() -> {
                runAfter.run();
                window.close();
            });
        } else {
            action.runAfter(window::close);
        }
        menuBar().actions(action.right());
        return this;
    }

    public AxWindow buttonPrimary(AxAction action) {
        ShortcutListener shortcut = action.shortcutListenerEnter();
        if (shortcut != null) addShortcutListener(shortcut);
        buttonAndClose(action.primary());
        return this;
    }

    public AxWindow closeListener(Window.CloseListener listener) {
        window.addCloseListener(listener);
        return this;
    }

    public AxWindow manualFocus() {
        manualFocus = true;
        return this;
    }

}
