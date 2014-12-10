package cz.req.ax;

import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.*;

public abstract class AxView extends CssLayout implements View {

    AxMenuBar menuBar;
    TabSheet tabSheet;

    protected AxView() {
        String name = getClass().getSimpleName();
        name = name.replaceAll("View", "").toLowerCase();
        setStyleName(name);
        setSizeFull();
    }

    public void addComponent(Component component, String styleName) {
        component.setStyleName(styleName);
        super.addComponent(component);
    }

    public void addComponentFull(Component component, String styleName) {
        component.setSizeFull();
        addComponent(component, styleName);
    }

    //TODO move to component factory
    public Button button(Resource icon, Button.ClickListener listener) {
        Button button = new Button();
        button.setIcon(icon);
        button.addClickListener(listener);
        return button;
    }

    public HorizontalLayout horizontalLayout(Component... comps) {
        HorizontalLayout layout = new HorizontalLayout(comps);
        addComponent(layout);
        return layout;
    }

    public AxMenuBar menuBar() {
        if (menuBar == null) {
            menuBar = new AxMenuBar();
            menuBar.setStyleName("menuBar");
            menuBar.setWidth(100, Unit.PERCENTAGE);
            addComponent(menuBar);
        }
        return menuBar;
    }

    public TabSheet tabSheet() {
        if (tabSheet == null) {
            tabSheet = new TabSheet();
            menuBar.setStyleName("addTabSheet");
            tabSheet.setSizeFull();
            tabSheet.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {
                @Override
                public void selectedTabChange(TabSheet.SelectedTabChangeEvent event) {
                    if (event.getComponent() instanceof RefreshListener) {
                        final RefreshListener listener = (RefreshListener) event.getComponent();
                        event.getComponent();
                        listener.refresh();
                    }
                }
            });
            addComponent(tabSheet);
        }
        return tabSheet;
    }

    protected void navigateTo(String view) {
        getUI().getNavigator().navigateTo(view);
    }

    protected void navigateTo(String view, Object param) {
        getUI().getNavigator().navigateTo(view + "/" + param.toString());
    }

    public TabSheet.Tab addTabSheet(String caption, FontAwesome awesome, final Component component) {
        return tabSheet().addTab(component, caption, awesome);
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
