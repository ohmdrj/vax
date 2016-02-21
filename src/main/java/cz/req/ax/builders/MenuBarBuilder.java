package cz.req.ax.builders;

import com.vaadin.ui.MenuBar;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 19.2.2016
 */
public class MenuBarBuilder extends ComponentBuilder<MenuBar, MenuBarBuilder> {

    public MenuBarBuilder() {
        super(new MenuBar(), true);
    }

    public MenuBarBuilder(MenuBar target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public MenuBarBuilder html() {
        target.setHtmlContentAllowed(true);
        return this;
    }

    public MenuBarBuilder autoOpen(boolean autoOpen) {
        target.setAutoOpen(autoOpen);
        return this;
    }

    public MenuBarBuilder autoOpen() {
        return autoOpen(true);
    }

    public MenuItemBuilder item() {
        return new MenuItemBuilder(target);
    }

    public MenuItemBuilder item(String caption) {
        return item().caption(caption);
    }

    public MenuBuilder menu() {
        return new MenuBuilder(target);
    }

    public MenuBuilder menu(String caption) {
        return menu().caption(caption);
    }

    public MenuBarBuilder link() {
        return style("link");
    }

}
