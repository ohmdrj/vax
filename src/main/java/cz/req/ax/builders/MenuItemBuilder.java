package cz.req.ax.builders;

import com.vaadin.server.Resource;
import com.vaadin.ui.MenuBar;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 15.2.2016
 */
public class MenuItemBuilder extends AxBuilder<MenuBar.MenuItem, MenuItemBuilder> {

    public MenuItemBuilder(MenuBar menuBar) {
        super(menuBar.addItem("", null), true);
    }

    public MenuItemBuilder(MenuBar.MenuItem parentItem) {
        super(parentItem.addItem("", null), true);
    }

    public MenuItemBuilder(MenuBar.MenuItem target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public MenuItemBuilder caption(String caption) {
        target.setText(caption);
        return this;
    }

    public MenuItemBuilder icon(Resource icon) {
        target.setIcon(icon);
        return this;
    }

    public MenuItemBuilder description(String description) {
        target.setDescription(description);
        return this;
    }

    public MenuItemBuilder style(String style) {
        target.setStyleName(style);
        return this;
    }

    public MenuItemBuilder enabled(boolean enabled) {
        target.setEnabled(enabled);
        return this;
    }

    public MenuItemBuilder enabled() {
        return enabled(true);
    }

    public MenuItemBuilder disabled() {
        return enabled(false);
    }

    public MenuItemBuilder visible(boolean visible) {
        target.setVisible(visible);
        return this;
    }

    public MenuItemBuilder visible() {
        return visible(true);
    }

    public MenuItemBuilder hidden() {
        return visible(false);
    }

    public MenuItemBuilder checkable(boolean checkable) {
        target.setCheckable(checkable);
        return this;
    }

    public MenuItemBuilder checkable() {
        return checkable(true);
    }

    public MenuItemBuilder uncheckable() {
        return checkable(false);
    }

    public MenuItemBuilder checked(boolean checked) {
        target.setChecked(checked);
        return this;
    }

    public MenuItemBuilder checked() {
        return checked(true);
    }

    public MenuItemBuilder unchecked() {
        return checked(false);
    }

    public MenuItemBuilder command(MenuBar.Command command) {
        target.setCommand(command);
        return this;
    }

    public MenuItemBuilder command(Runnable command) {
        return command(mi -> command.run());
    }

}
