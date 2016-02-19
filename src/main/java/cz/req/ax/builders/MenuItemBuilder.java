package cz.req.ax.builders;

import com.vaadin.ui.MenuBar;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 19.2.2016
 */
public class MenuItemBuilder extends AbstractMenuItemBuilder<MenuItemBuilder> {

    public MenuItemBuilder(MenuBar parent) {
        super(parent, true);
    }

    public MenuItemBuilder(MenuBar.MenuItem parent) {
        super(parent, true);
    }

    public MenuItemBuilder(MenuBar parent, boolean useDefaults) {
        super(parent, useDefaults);
    }

    public MenuItemBuilder(MenuBar.MenuItem parent, boolean useDefaults) {
        super(parent, useDefaults);
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
        return checkable();
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

    @Override
    public MenuItemBuilder item() {
        return new MenuItemBuilder(target.getParent());
    }

    @Override
    public MenuBuilder menu() {
        return new MenuBuilder(target.getParent());
    }

}
