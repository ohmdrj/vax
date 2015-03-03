package cz.req.ax;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AxWindow extends Window implements Container, Navigation {

    ContainerRoot containerRoot;
    VerticalLayout layout;

    public AxWindow() {
        layout = new VerticalLayout();
        containerRoot = new ContainerRoot(layout);
        addStyleName("window-headerless");
        setClosable(false);
        setResizable(false);
        setContent(layout);
    }

    @Override
    public ContainerRoot getRoot() {
        return containerRoot;
    }

    @Override
    public Object getNavigationParameter() {
        return null;
    }

    public AxWindow show() {
        center();
        UI.getCurrent().addWindow(this);
        return this;
    }

    public AxWindow modal() {
        setModal(true);
        return this;
    }

    public AxWindow size(Integer width, Integer height) {
        if (width != null) setWidth(width, Unit.PIXELS);
        if (height != null) setHeight(height, Unit.PIXELS);
        return this;
    }

    public AxWindow style(String styleName) {
        addStyleName(styleName);
        return this;
    }

    @Override
    public AxWindow components(Component... components) {
        Container.super.components(components);
        return this;
    }

    @Override
    public AxWindow layoutCss() {
        Container.super.layoutCss();
        return this;
    }

    public AxWindow buttonClose() {
        return buttonClose("Zavřít");
    }

    public AxWindow buttonClose(String caption) {
        menuBar().actions(new AxAction().caption(caption).icon(FontAwesome.TIMES)
                .right().run(this::close));
        return this;
    }

    public AxWindow buttonAndClose(AxAction action) {
        action.runAfter(this::close);
        menuBar().actions(action);
        return this;
    }
}
