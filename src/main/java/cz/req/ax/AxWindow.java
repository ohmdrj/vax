package cz.req.ax;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

public class AxWindow extends Window {

    AxMenuBar menuBar;
    VerticalLayout layout;

    public AxWindow() {

        addStyleName("window-headerless");
        setClosable(false);
        setResizable(false);
        layout = new VerticalLayout();
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(layout);
    }

    public MenuButton getCloseButton() {
        return new MenuButton("Zavřít", FontAwesome.TIMES) {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                close();
            }
        };
    }

    public MenuBar.MenuItem addCloseButton() {
        MenuBar.MenuItem menuItem = addMenuCommand(getCloseButton());
        menuItem.setStyleName("right");
        return menuItem;
    }

    public void addComponent(Component c) {
        layout.addComponent(c);
    }


    public AxMenuBar menuBar() {
        if (menuBar == null) {
            menuBar = new AxMenuBar();
//                menuBar.setStyleName("menuBar");
            menuBar.setWidth(100, Unit.PERCENTAGE);
            layout.addComponent(menuBar);
        }
        return menuBar;
    }

    public MenuBar.MenuItem addMenuNavigate(String caption, FontAwesome awesome, String view) {
        return menuBar().addNavigate(caption, awesome, view);
    }

    public MenuBar.MenuItem addMenuCommand(MenuButton action) {
        return menuBar().addCommand(action);
    }

    public MenuBar.MenuItem addMenuCommand(String caption, FontAwesome awesome, MenuBar.Command command) {
        return menuBar().addCommand(caption, awesome, command);
    }

}
