package cz.req.ax.builders;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import cz.req.ax.AxUtils;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 13.2.2016
 */
public class AbstractButtonBuilder<C extends Button, B extends AbstractButtonBuilder<C, B>> extends ComponentBuilder<C, B> {

    public AbstractButtonBuilder(C target, boolean useDefaults) {
        super(target, useDefaults);
        captionSuffix(null);
    }

    public B html() {
        target.setHtmlContentAllowed(true);
        return (B) this;
    }

    public B disableOnClick() {
        target.setDisableOnClick(true);
        return (B) this;
    }

    public B onClick(Runnable listener) {
        target.addClickListener(e -> listener.run());
        return (B) this;
    }

    public B clickShortcut(int key, int... modifiers) {
        target.setClickShortcut(key, modifiers);
        return (B) this;
    }

    public B enterKeyShortcut() {
        return clickShortcut(ShortcutAction.KeyCode.ENTER);
    }

    public B escKeyShortcut() {
        return clickShortcut(ShortcutAction.KeyCode.ESCAPE);
    }

    public B link() {
        return style("link");
    }

    public B primary() {
        return style("primary");
    }

    public B secondary() {
        return style("secondary");
    }

    public B small() {
        return style("small");
    }

    public B rightAligned() {
        return style("right-aligned");
    }

    public B leftAligned() {
        return style("left-aligned");
    }

}
