package cz.req.ax.builders;

import com.vaadin.ui.UI;
import cz.req.ax.action.AxAction;
import cz.req.ax.ui.AxWindow;
import cz.req.ax.ui.AxWindowButton;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 14.2.2016
 */
public class AxWindowBuilder extends WindowBuilder<AxWindow, AxWindowBuilder> {

    public AxWindowBuilder() {
        super(new AxWindow(), false);
    }

    public AxWindowBuilder(AxWindow target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public AxWindowBuilder autoFocusOnFirstField() {
        target.setAutoFocusMode(AxWindow.AutoFocusMode.FIRST_FIELD_OR_WINDOW);
        return this;
    }

    public AxWindowBuilder disableAutoFocus() {
        target.setAutoFocusMode(AxWindow.AutoFocusMode.DISABLED);
        return this;
    }

    public AxWindowBuilder closeButton(String caption) {
        target.addCloseButton(caption);
        return this;
    }

    public AxWindowBuilder closeButton() {
        target.removeCloseButton();
        target.addCloseButton();
        return this;
    }

    public AxWindowBuilder hideCloseButton() {
        target.removeCloseButton();
        return this;
    }

    public AxWindowButtonBuilder button() {
        return target.addButton();
    }

    public AxWindowButtonBuilder button(String caption) {
        return button().caption(caption);
    }

    public AxWindowButtonBuilder button(AxWindowButton button) {
        return target.addButton(button);
    }

    public AxWindowButtonBuilder button(AxWindowButtonBuilder builder) {
        return button(builder.get());
    }

    public AxWindowButtonBuilder button(AxAction<?> action) {
        return target.addButton(action);
    }

    public AxWindowButtonBuilder button(AxActionBuilder<?> builder) {
        return button(builder.get());
    }

    public void show(UI ui) {
        target.show(ui);
    }

    public void show() {
        target.show();
    }

}
