package cz.req.ax;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

/**
 * @deprecated use {@link Ax} methods
 */
@Deprecated
@FunctionalInterface
public interface Components {

    String getId();

    /**
     * @deprecated use {@link Ax#cssLayout(Component...)} - Ax.layout(components)
     */
    @Deprecated
    default CssLayout cssLayout(Component... components) {
        CssLayout layout = new CssLayout(components);
        layout.setSizeUndefined();
        return layout;
    }

    /**
     * @deprecated use {@link Ax#cssLayout(Component...)} - Ax.cssLayout(components).style(style).get()
     */
    @Deprecated
    default CssLayout cssLayout(String name, Component... components) {
        CssLayout layout = cssLayout(components);
        layout.setStyleName(name);
        return layout;
    }

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

    /**
     * @deprecated use {@link Ax#icon(Resource)} - Ax.icon(resource).style("image").get()
     */
    @Deprecated
    default Component newImage(Resource resource) {
//        Image image = new Image("", resource);
//        return image;
        Label label = new Label();
        label.addStyleName("image");
        label.setSizeUndefined();
        label.setIcon(resource);
        return label;
    }

    /**
     * @deprecated use {@link Ax#label(String)} - Ax.label(value).get()
     */
    @Deprecated
    default Label newLabel(String content) {
        return newLabel(content, null);
    }

    /**
     * @deprecated use {@link Ax#label(String)} - Ax.label(value).style(style).get()
     */
    @Deprecated
    default Label newLabel(String content, String style) {
        Label label = new Label(content);
        label.setSizeUndefined();
        label.addStyleName(style);
        return label;
    }

    /**
     * @deprecated use {@link Ax#label(String)} - Ax.label(value).icon(icon).style(style).get()
     */
    @Deprecated
    default Label newLabel(FontAwesome icon, String content, String style) {
        Label label = new Label(content);
        label.setIcon(icon);
        label.setSizeUndefined();
        label.addStyleName(style);
        return label;
    }

    /**
     * @deprecated use {@link Ax#caption(String)} - Ax.caption(value).get()
     */
    @Deprecated
    default Label newCaption(String content) {
        return newLabel(content, "v-caption");
    }

    default Component undefine(Component component) {
        component.setSizeUndefined();
        return component;
    }

}
