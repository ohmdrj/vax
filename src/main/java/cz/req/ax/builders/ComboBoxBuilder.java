package cz.req.ax.builders;

import com.vaadin.server.Resource;
import com.vaadin.ui.ComboBox;
import cz.req.ax.ui.AxComboBox;

import java.util.function.Function;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class ComboBoxBuilder extends AbstractSelectBuilder<ComboBox, ComboBoxBuilder> {

    public ComboBoxBuilder() {
        super(new AxComboBox<>(), true);
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

    public <V> ComboBoxBuilder itemCaption(Function<V, String> function) {
        if (target instanceof AxComboBox) {
            ((AxComboBox<V>) target).setItemCaptionFunction(function);
        }
        return this;
    }

    public <V> ComboBoxBuilder itemIcon(Function<V, Resource> function) {
        if (target instanceof AxComboBox) {
            ((AxComboBox<V>) target).setItemIconFunction(function);
        }
        return this;
    }

}
