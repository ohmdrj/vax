package cz.req.ax.builders;

import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import cz.req.ax.action.AxAction;
import cz.req.ax.ui.AxWindow;
import cz.req.ax.ui.AxWindowButton;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 13.2.2016
 */
public class AxWindowButtonBuilder extends AbstractButtonBuilder<AxWindowButton, AxWindowButtonBuilder> {

    public AxWindowButtonBuilder() {
        this(new AxWindowButton(), false);
    }

    public AxWindowButtonBuilder(AxWindowButton target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public AxWindowButtonBuilder closesWindow(boolean closes) {
        target.setCloseAfterClick(closes);
        return this;
    }

    public AxWindowButtonBuilder window(AxWindow window) {
        target.setWindow(window);
        return this;
    }

    public AxWindowButtonBuilder closesWindow() {
        return closesWindow(true);
    }

    public AxWindowButtonBuilder keepsWindow() {
        return closesWindow(false);
    }

    public AxWindowButtonBuilder onClick(Consumer<Window> listener) {
        target.addWindowClickListener(e -> listener.accept(e.getWindow()));
        return this;
    }

    public AxWindowButtonBuilder onClick(BooleanSupplier listenerWithResult) {
        return onClick(window -> {
            if (listenerWithResult.getAsBoolean()) {
                window.close();
            }
        });
    }

    public AxWindowButtonBuilder alignLeft() {
        target.setAlignment(AxWindow.Align.LEFT);
        return this;
    }

    public AxWindowButtonBuilder alignRight() {
        target.setAlignment(AxWindow.Align.RIGHT);
        return this;
    }

    public AxWindowButtonBuilder order(int order) {
        target.setOrder(order);
        return this;
    }

    public AxWindowButtonBuilder button() {
        return window().button();
    }

    public AxWindowButtonBuilder button(String caption) {
        return window().button(caption);
    }

    public AxWindowButtonBuilder button(AxWindowButton button) {
        return window().button(button);
    }

    public AxWindowButtonBuilder button(AxWindowButtonBuilder builder) {
        return button(builder.get());
    }

    public AxWindowButtonBuilder button(AxAction<?> action) {
        return window().button(action);
    }

    public AxWindowButtonBuilder button(AxActionBuilder<?> builder) {
        return button(builder.get());
    }

    public void show(UI ui) {
        window().show(ui);
    }

    public void show() {
        window().show();
    }

    public AxWindowBuilder window() {
        return new AxWindowBuilder(target.getWindow(), false);
    }

}
