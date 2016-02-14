package cz.req.ax.builders;

import com.vaadin.ui.Button;
import cz.req.ax.AxUtils;

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

    public ButtonBuilder htmlAllowed() {
        target.setHtmlContentAllowed(true);
        return this;
    }

    public ButtonBuilder disableOnClick() {
        target.setDisableOnClick(true);
        return this;
    }

    public ButtonBuilder onClick(Runnable listener) {
        target.addClickListener(e -> AxUtils.safeRun(listener));
        return this;
    }

    public ButtonBuilder clickShortcut(int key, int... modifiers) {
        target.setClickShortcut(key, modifiers);
        return this;
    }

    public ButtonBuilder link() {
        style("link");
        return this;
    }

}
