package cz.req.ax.builders;

import com.vaadin.server.Resource;
import com.vaadin.ui.MenuBar;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 19.2.2016
 */
public abstract class AbstractMenuItemBuilder<B extends AbstractMenuItemBuilder<B>> extends AxBuilder<MenuBar.MenuItem, B> {

    protected MenuBar menuBar;

    public AbstractMenuItemBuilder(MenuBar menuBar, boolean useDefaults) {
        super(menuBar.addItem("", null), useDefaults);
        this.menuBar = menuBar;
    }

    public AbstractMenuItemBuilder(MenuBar.MenuItem parentItem, boolean useDefaults) {
        super(parentItem.addItem("", null), useDefaults);
    }

    public B caption(String caption) {
        target.setText(caption);
        return (B) this;
    }

    public B icon(Resource icon) {
        target.setIcon(icon);
        return (B) this;
    }

    public B description(String description) {
        target.setDescription(description);
        return (B) this;
    }

    public B style(String style) {
        target.setStyleName(style);
        return (B) this;
    }

    public B enabled(boolean enabled) {
        target.setEnabled(enabled);
        return (B) this;
    }

    public B enabled() {
        return enabled(true);
    }

    public B disabled() {
        return enabled(false);
    }

    public B visible(boolean visible) {
        target.setVisible(visible);
        return (B) this;
    }

    public B visible() {
        return visible(true);
    }

    public B hidden() {
        return visible(false);
    }

    public abstract MenuItemBuilder item();

    public MenuItemBuilder item(String caption) {
        return item().caption(caption);
    }

    public abstract MenuBuilder menu();

    public MenuBuilder menu(String caption) {
        return menu().caption(caption);
    }

    public MenuBuilder parent() {
        return new MenuBuilder(target.getParent(), false);
    }

}
