package cz.req.ax.builders;

import com.vaadin.ui.CheckBox;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class CheckBoxBuilder extends FieldBuilder<Boolean, CheckBox, CheckBoxBuilder> {

    public CheckBoxBuilder() {
        super(new CheckBox());
    }

    public CheckBoxBuilder(String caption) {
        super(new CheckBox(caption));
    }

    public CheckBoxBuilder(CheckBox field) {
        super(field);
    }

    public CheckBoxBuilder(CheckBox field, boolean useDefaults) {
        super(field, useDefaults);
    }

}
