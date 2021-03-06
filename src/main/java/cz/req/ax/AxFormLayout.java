package cz.req.ax;

import com.google.common.base.Strings;
import com.vaadin.ui.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <strong>Nepoužívat!</strong> Lze nahradit pomocí klasického {@link FormLayout} a {@link Ax}.
 * <pre>
 *      FormLayout layout = new FormLayout();
 *      layout.addComponent(Ax.caption("Popisek").value("Read-only hodnota").get());
 *      layout.addComponent(Ax.textField("Popisek").value("Editovatelna hodnota").get());
 *      layout.addComponent(binder.buildAndBind("Popisek", "Property"));
 * </pre>
 *
 * @deprecated použít {@link FormLayout} + {@link Ax}
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 8.1.2016
 */
@Deprecated
public class AxFormLayout extends CssLayout {

    private String captionSuffix;
    private Map<Component, Component> rowMap = new HashMap<>(); // Value component -> Row
    
    public AxFormLayout() {
        setCaptionSuffix(":");
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

    @Override
    public void addComponent(Component component) {
        addRow(component);
    }

    public RowBuilder addRow() {
        return new RowBuilder();
    }

    public RowBuilder addRow(String caption) {
        return addRow().caption(caption);
    }

    public void addRow(Component component) {
        if (component instanceof Field) {
            addRow().field((Field) component);
        } else {
            addRow().component(component);
        }
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
                this.caption = caption;
                if (!Strings.isNullOrEmpty(captionSuffix) && !caption.endsWith(captionSuffix)) {
                    this.caption += captionSuffix;
                }
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
            component(Ax.label(value).get());
        }

        public void field(Field field) {
            caption(field instanceof CheckBox ? null : field.getCaption());
            required(field.isRequired());
            component(field);
        }

        public void component(Component component) {
            Label captionLabel = Ax.label(caption).get();
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

            AxFormLayout.super.addComponent(row);
        }

    }

}
