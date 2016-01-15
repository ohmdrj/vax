package cz.req.ax;

import com.google.common.base.Strings;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 8.1.2016
 */
public class AxFormLayout extends CssLayout implements Components {

    private boolean hideEmptyRows;
    
    public AxFormLayout() {
        addStyleName("form-layout");
    }

    public void hideEmptyRows() {
        this.hideEmptyRows = true;
    }
    
    public void makeVertical() {
        addStyleName("vertical");
    }

    public RowBuilder addRow() {
        return new RowBuilder();
    }

    public class RowBuilder {

        private String caption;
        private String style;
        private boolean required;
        private boolean hideEmpty = hideEmptyRows;

        public RowBuilder caption(String caption) {
            this.caption = caption + ":";
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
            if (!(Strings.isNullOrEmpty(value) && hideEmpty)) {
                component(newLabel(value));
            }
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

            if (!(component instanceof Label)) {
                component = new CssLayout(component);
            }

            component.addStyleName("form-value");

            CssLayout row = new CssLayout(captionLabel, component);
            row.addStyleName("form-row");

            if (style != null) {
                component.addStyleName("form-value-" + style);
                row.addStyleName("form-row-" + style);
            }

            AxFormLayout.this.addComponent(row);
        }

    }

}
