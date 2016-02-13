package cz.req.ax.builders;

import com.vaadin.ui.ComboBox;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class ComboBoxBuilder extends AbstractSelectBuilder<ComboBox, ComboBoxBuilder> {

    public ComboBoxBuilder() {
        super(new ComboBox());
    }

    public ComboBoxBuilder(String caption) {
        super(new ComboBox(caption));
    }

    public ComboBoxBuilder(ComboBox field) {
        super(field);
    }

    public ComboBoxBuilder(ComboBox field, boolean useDefaults) {
        super(field, useDefaults);
    }

    public ComboBoxBuilder inputPrompt(String inputPrompt) {
        component.setInputPrompt(inputPrompt);
        return this;
    }

    public ComboBoxBuilder pageLength(int pageLength) {
        component.setPageLength(pageLength);
        return this;
    }

    public ComboBoxBuilder textInputAllowed(boolean allowed) {
        component.setTextInputAllowed(allowed);
        return this;
    }

    public ComboBoxBuilder textInputAllowed() {
        return textInputAllowed(true);
    }

    public ComboBoxBuilder textInputProhibited() {
        return textInputAllowed(false);
    }

}
