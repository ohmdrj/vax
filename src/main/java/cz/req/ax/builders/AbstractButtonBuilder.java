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

    public B primary() {
        return style("primary");
    }

    public B secondary() {
        return style("secondary");
    }

    public B friendly() {
        return style("friendly");
    }

    public B danger() {
        return style("danger");
    }

    public B borderless() {
        return style("borderless");
    }

    public B borderlessColored() {
        return style("borderless-colored");
    }

    public B quiet() {
        return style("quiet");
    }

    public B link() {
        return style("link");
    }

    public B tiny() {
        return style("tiny");
    }

    public B small() {
        return style("small");
    }

    public B large() {
        return style("large");
    }

    public B huge() {
        return style("huge");
    }

    public B iconAlignRight() {
        return style("icon-align-right");
    }

    public B iconAlignTop() {
        return style("icon-align-top");
    }

}
