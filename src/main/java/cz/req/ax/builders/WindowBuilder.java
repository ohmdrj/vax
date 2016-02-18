package cz.req.ax.builders;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import java.util.function.Consumer;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 14.2.2016
 */
public class WindowBuilder<W extends Window, B extends WindowBuilder<W, B>> extends ComponentBuilder<W, B> {

    public WindowBuilder(W target, boolean useDefaults) {
        super(target, useDefaults);
        captionSuffix(null);
    }

    public B content(Component component) {
        target.setContent(component);
        return (B) this;
    }

    public B centered() {
        target.center();
        return (B) this;
    }

    public B resizable(boolean resizable) {
        target.setResizable(resizable);
        return (B) this;
    }

    public B resizable() {
        return resizable(true);
    }

    public B unresizable() {
        return resizable(false);
    }

    public B closeable(boolean closeable) {
        target.setClosable(closeable);
        return (B) this;
    }

    public B closeable() {
        return closeable(true);
    }

    public B uncloseable() {
        return closeable(false);
    }

    public B draggable(boolean draggable) {
        target.setDraggable(draggable);
        return (B) this;
    }

    public B draggable() {
        return draggable(true);
    }

    public B undraggable() {
        return draggable(false);
    }

    public B modal(boolean modal) {
        target.setModal(modal);
        return (B) this;
    }

    public B modal() {
        return modal(true);
    }

    public B mode(WindowMode mode) {
        target.setWindowMode(mode);
        return (B) this;
    }

    public B maximized() {
        return mode(WindowMode.MAXIMIZED);
    }

    public B left(int x) {
        target.setPositionX(x);
        return (B) this;
    }

    public B top(int y) {
        target.setPositionY(y);
        return (B) this;
    }

    public B right(int x) {
        UI ui = UI.getCurrent();
        if (ui != null && target.getWidthUnits() == Sizeable.Unit.PIXELS) {
            int pageWidth = ui.getPage().getBrowserWindowWidth();
            int windowWidth = (int) target.getWidth();
            return left(pageWidth - windowWidth - x);
        }
        return (B) this;
    }

    public B bottom(int y) {
        UI ui = UI.getCurrent();
        if (ui != null && target.getHeightUnits() == Sizeable.Unit.PIXELS) {
            int pageHeight = ui.getPage().getBrowserWindowHeight();
            int windowHeight = (int) target.getHeight();
            return left(pageHeight - windowHeight - y);
        }
        return (B) this;
    }

    public B position(int x, int y) {
        return left(x).top(y);
    }

    public B closeShortcut(int key, int... modifiers) {
        target.setCloseShortcut(key, modifiers);
        return (B) this;
    }

    public B noCloseShortcut() {
        target.removeCloseShortcut();
        return (B) this;
    }

    public B closeOnEscape() {
        return closeShortcut(ShortcutAction.KeyCode.ESCAPE);
    }

    public B onClose(Runnable listener) {
        target.addCloseListener(e -> listener.run());
        return (B) this;
    }

    public B onResize(Consumer<Window> listener) {
        target.addResizeListener(e ->  listener.accept(e.getWindow()));
        return (B) this;
    }

    public B onModeChange(Consumer<Window> listener) {
        target.addWindowModeChangeListener(e ->  listener.accept(e.getWindow()));
        return (B) this;
    }

}
