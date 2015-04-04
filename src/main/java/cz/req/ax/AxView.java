package cz.req.ax;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TabSheet;

public abstract class AxView extends CssLayout implements View, Container, Navigation, Components {

    ContainerRoot containerRoot;
    TabSheet tabSheet;

    protected AxView() {
        this(false);
    }

    protected AxView(boolean wrap) {
        containerRoot = new ContainerRoot(this, wrap);
        String name = getClass().getSimpleName();
        name = name.replaceAll("View", "-view").toLowerCase();
        setStyleName("page-root");
        addStyleName(name);
        setSizeUndefined();
//        setSizeFull();
    }

    @Override
    public ContainerRoot getRoot() {
        return containerRoot;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //TODO Parse navigation parameter??
    }

    public AxView actions(AxAction... actions) {
        Container.super.menuActions(actions);
        return this;
    }

    public AxView components(Component... components) {
        Container.super.mainComponents(components);
        return this;
    }

    public AxView components(String layoutName, Component... components) {
        //TODO Support wrap??
        Container.super.bodyLayout(layoutName).addComponents(components);
        return this;
    }

    public TabSheet tabSheet() {
        if (tabSheet == null) {
            tabSheet = new TabSheet();
            tabSheet.setWidth(100, Unit.PERCENTAGE);
            tabSheet.setHeightUndefined();
            tabSheet.addSelectedTabChangeListener(event -> Refresh.tryRefresh(tabSheet.getSelectedTab()));
            components(tabSheet);
        }
        return tabSheet;
    }

    public TabSheet.Tab addTabSheet(String caption, FontAwesome awesome, final ComponentWrapper component) {
        tabSheet().addSelectedTabChangeListener(event -> {
            if (tabSheet().getSelectedTab().equals(component.getComponent())) {
                Refresh.tryRefresh(component);
            }
        });
        return tabSheet().addTab(component.getComponent(), caption, awesome);
    }

    public TabSheet.Tab addTabSheet(String caption, FontAwesome awesome, Component component) {
        return tabSheet().addTab(component, caption, awesome);
    }

}
