package cz.req.ax.builders;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import cz.req.ax.Ax;
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

    public B primary() {
        return style(Ax.PRIMARY);
    }

    public B secondary() {
        return style(Ax.SECONDARY);
    }

    public B friendly() {
        return style(Ax.FRIENDLY);
    }

    public B danger() {
        return style(Ax.DANGER);
    }

    public B borderless() {
        return style(Ax.BORDERLESS);
    }

    public B borderlessColored() {
        return style(Ax.BORDERLESS_COLORED);
    }

    public B quiet() {
        return style(Ax.QUIET);
    }

    public B link() {
        return style(Ax.LINK);
    }

    public B tiny() {
        return style(Ax.TINY);
    }

    public B small() {
        return style(Ax.SMALL);
    }

    public B large() {
        return style(Ax.LARGE);
    }

    public B huge() {
        return style(Ax.HUGE);
    }

    public B iconAlignRight() {
        return style(Ax.ICON_ALIGN_RIGHT);
    }

    public B iconAlignTop() {
        return style(Ax.ICON_ALIGN_TOP);
    }

}
