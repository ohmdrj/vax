package cz.req.ax;

import com.google.common.base.Strings;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 8.1.2016
 */
public class AxFormLayout extends CssLayout implements Components {

    private String captionSuffix;
    private Map<Component, Component> rowMap = new HashMap<>(); // Value component -> Row
    
    public AxFormLayout() {
        addStyleName("form-layout");
    }

    public void hideEmptyRows() {
        for (Map.Entry<Component, Component> e: rowMap.entrySet()) {
            e.getValue().setVisible(!isEmpty(e.getKey()));
        }
    }
    
    public void makeVertical() {
        addStyleName("vertical");
    }

    public void setCaptionSuffix(String captionSuffix) {
        this.captionSuffix = captionSuffix;
    }

    public RowBuilder addRow() {
        return new RowBuilder();
    }

    public RowBuilder addRow(String caption) {
        return addRow().caption(caption);
    }

    public void addRow(Field field) {
        addRow().field(field);
    }

    private boolean isEmpty(Component component) {
        return !component.isVisible()
                || (component instanceof Label && Strings.isNullOrEmpty(((Label) component).getValue()));
    }

    public class RowBuilder {

        private String caption;
        private String style;
        private boolean required;
        private boolean hideEmpty;

        public RowBuilder caption(String caption) {
            if (!Strings.isNullOrEmpty(caption)) {
                this.caption = caption + Strings.nullToEmpty(captionSuffix);
            }
            return this;
        }

        public RowBuilder style(String style) {
            this.style = style;
            return this;
        }

        public RowBuilder required(boolean required) {
            this.required = required;
            return this;
        }

        public RowBuilder hideEmpty() {
            this.hideEmpty = true;
            return this;
        }

        public void value(String value) {
            component(newLabel(value));
        }

        public void field(Field field) {
            caption(field.getCaption()).required(field.isRequired()).component(field);
        }

        public void component(Component component) {
            Label captionLabel = newLabel(caption);
            captionLabel.addStyleName("form-caption");
            if (required) {
                captionLabel.addStyleName("required");
            }

            if (style != null) {
                captionLabel.addStyleName("form-caption-" + style);
                component.addStyleName(style);
            }

            Component wrapper = component;
            if (!(component instanceof Label)) {
                wrapper = new CssLayout(component);
            }

            wrapper.addStyleName("form-value");

            CssLayout row = new CssLayout(captionLabel, wrapper);
            row.addStyleName("form-row");

            if (style != null) {
                wrapper.addStyleName("form-value-" + style);
                row.addStyleName("form-row-" + style);
            }

            row.setVisible(!(hideEmpty && isEmpty(component)));
            rowMap.put(component, row);

            AxFormLayout.this.addComponent(row);
        }

    }

}
