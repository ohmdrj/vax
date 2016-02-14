package cz.req.ax.builders;

import com.vaadin.ui.ComboBox;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class ComboBoxBuilder extends AbstractSelectBuilder<ComboBox, ComboBoxBuilder> {

    public ComboBoxBuilder() {
        super(new ComboBox(), true);
    }

    public ComboBoxBuilder(ComboBox target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public ComboBoxBuilder inputPrompt(String inputPrompt) {
        target.setInputPrompt(inputPrompt);
        return this;
    }

    public ComboBoxBuilder pageLength(int pageLength) {
        target.setPageLength(pageLength);
        return this;
    }

    public ComboBoxBuilder textInputAllowed(boolean allowed) {
        target.setTextInputAllowed(allowed);
        return this;
    }

    public ComboBoxBuilder textInputAllowed() {
        return textInputAllowed(true);
    }

    public ComboBoxBuilder textInputProhibited() {
        return textInputAllowed(false);
    }

}
