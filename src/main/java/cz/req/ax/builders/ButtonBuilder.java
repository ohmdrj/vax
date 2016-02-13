package cz.req.ax.builders;

import com.vaadin.ui.Button;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 13.2.2016
 */
public class ButtonBuilder extends ComponentBuilder<Button, ButtonBuilder> {

    public ButtonBuilder() {
        super(new Button(), true);
    }

    public ButtonBuilder(Button target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public ButtonBuilder htmlContent() {
        target.setHtmlContentAllowed(true);
        return this;
    }

    public ButtonBuilder disableOnClick() {
        target.setDisableOnClick(true);
        return this;
    }

    public ButtonBuilder onClick(Runnable listener) {
        target.addClickListener(e -> listener.run());
        return this;
    }

    public ButtonBuilder clickShortcut(int key, int... modifiers) {
        target.setClickShortcut(key, modifiers);
        return this;
    }

}
