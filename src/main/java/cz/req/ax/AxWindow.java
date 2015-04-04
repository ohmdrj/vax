package cz.req.ax;

import com.vaadin.server.ErrorHandler;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class AxWindow extends Window implements Container, Navigation {

    ContainerRoot containerRoot;

    public AxWindow() {
        CssLayout layout = new CssLayout();
        layout.setStyleName("window-root");
        addStyleName("window-headerless");
        setClosable(false);
        setResizable(false);
        setContent(layout);
        containerRoot = new ContainerRoot(layout, false);
    }

    @Override
    public ContainerRoot getRoot() {
        return containerRoot;
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

    public AxWindow components(Component... components) {
        Container.super.mainComponents(components);
        return this;
    }

    public AxWindow errorHandler(ErrorHandler errorHandler) {
        setErrorHandler(errorHandler);
        return this;
    }

    /*@Override
    public AxWindow mainPanel() {
        Container.super.mainPanel();
        return this;
    }*/

    public AxWindow buttonClose() {
        return buttonClose("Zavřít");
    }

    public AxWindow buttonClose(String caption) {
        menuBar().actions(new AxAction().caption(caption)//.icon(FontAwesome.TIMES)
                .right().run(this::close));
        return this;
    }

    public AxWindow buttonAndClose(AxAction action) {
        action.runAfter(this::close);
        menuBar().actions(action);
        return this;
    }
}
