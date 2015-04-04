package cz.req.ax;

import com.vaadin.server.Resource;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;

@FunctionalInterface
public interface Components {

    String getId();

    default <T extends AbstractLayout> T newLayout(Class<T> type, String style, Component... components) {
        try {
            T layout = type.newInstance();
            layout.setSizeUndefined();
            layout.setStyleName(style);
            if (components != null) layout.addComponents(components);
            return layout;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default Image newImage(Resource resource) {
        Image image = new Image("", resource);
        return image;
    }

    default Label newLabel(String content, String style) {
        Label label = new Label(content);
        label.addStyleName(style);
        return label;
    }

    default Component undefine(Component component) {
        component.setSizeUndefined();
        return component;
    }

}
