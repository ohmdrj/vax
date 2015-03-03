package cz.req.ax;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TabSheet;

public abstract class AxView extends CssLayout implements View, Container, Navigation {

    ContainerRoot containerRoot;
    TabSheet tabSheet;

    protected AxView() {
        containerRoot = new ContainerRoot(this);
        String name = getClass().getSimpleName();
        name = name.replaceAll("View", "-view").toLowerCase();
        addStyleName(name);
        setSizeUndefined();
//        setSizeFull();
    }

    @Override
    public Object getNavigationParameter() {
        return null;
    }

    @Override
    public ContainerRoot getRoot() {
        return containerRoot;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //TODO Parse navigation parameter??
    }

    @Override
    public AxView components(Component... components) {
        Container.super.components(components);
        return this;
    }

    public TabSheet tabSheet() {
        if (tabSheet == null) {
            tabSheet = new TabSheet();
            tabSheet.setSizeFull();
            tabSheet.addSelectedTabChangeListener(event -> Refresh.tryRefresh(event.getComponent()));
            components(tabSheet);
        }
        return tabSheet;
    }

    public TabSheet.Tab addTabSheet(String caption, FontAwesome awesome, Component component) {
        return tabSheet().addTab(component, caption, awesome);
    }

}
