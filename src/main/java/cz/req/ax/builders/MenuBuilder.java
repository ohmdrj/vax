package cz.req.ax.builders;

import com.vaadin.ui.MenuBar;
import cz.req.ax.Ax;
import org.springframework.util.Assert;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 19.2.2016
 */
public class MenuBuilder extends AbstractMenuItemBuilder<MenuBuilder> {

    public MenuBuilder(MenuBar parent) {
        super(parent, true);
    }

    public MenuBuilder(MenuBar.MenuItem parent) {
        super(parent, true);
    }

    public MenuBuilder(MenuBar parent, boolean useDefaults) {
        super(parent, useDefaults);
    }

    public MenuBuilder(MenuBar.MenuItem parent, boolean useDefaults) {
        super(parent, useDefaults);
    }

    public MenuBuilder more() {
        Assert.state(menuBar != null, "more can be only called for direct child of MenuBar");
        menuBar.setMoreMenuItem(target);
        return this;
    }

    @Override
    public MenuItemBuilder item() {
        return new MenuItemBuilder(target);
    }

    @Override
    public MenuBuilder menu() {
        return new MenuBuilder(target);
    }

    public MenuBuilder hideSubmenuIndicator() {
        return style(Ax.NO_SUBMENU_INDICATOR);
    }

    public MenuBuilder separator() {
        target.addSeparator();
        return this;
    }

}
