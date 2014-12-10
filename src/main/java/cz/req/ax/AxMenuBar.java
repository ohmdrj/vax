package cz.req.ax;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;

public class AxMenuBar extends MenuBar {

    public MenuBar.MenuItem addNavigate(String caption, FontAwesome awesome, final String view) {
        return addCommand(caption, awesome, new MenuBar.Command() {
                    @Override
                    public void menuSelected(MenuBar.MenuItem menuItem) {
                        getUI().getNavigator().navigateTo(view);
                    }
                }
        );
    }

    public MenuBar.MenuItem addCommand(MenuButton action) {
        return addCommand(action.getCaption(), action.getIcon(), action);
    }

    public MenuBar.MenuItem addCommand(String caption, FontAwesome awesome, MenuBar.Command command) {
        return addItem(caption, awesome, command);

    }
}