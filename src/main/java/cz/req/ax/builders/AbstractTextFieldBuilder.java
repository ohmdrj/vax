package cz.req.ax.builders;

import com.vaadin.ui.AbstractTextField;
import cz.req.ax.AxBinder;

import java.util.function.Consumer;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class AbstractTextFieldBuilder<F extends AbstractTextField, B extends AbstractTextFieldBuilder<F, B>>
        extends FieldBuilder<String, F, B> {

    public AbstractTextFieldBuilder(F target, boolean useDefaults) {
        super(target, useDefaults);
    }

    @Override
    protected void applyDefaults() {
        super.applyDefaults();
        nullProhibited();
        nullRepresentation("");
    }

    public B nullAllowed(boolean allowed) {
        target.setNullSettingAllowed(allowed);
        return (B) this;
    }
    
    public B nullAllowed() {
        return nullAllowed(true);
    }

    public B nullProhibited() {
        return nullAllowed(false);
    }

    public B nullRepresentation(String representation) {
        target.setNullRepresentation(representation);
        return (B) this;
    }
    
    public B maxLength(int maxLength) {
        target.setMaxLength(maxLength);
        return (B) this;
    }

    public B columns(int columns) {
        target.setColumns(columns);
        return (B) this;
    }

    public B maxLength(Integer length) {
        target.setMaxLength(length);
        return (B) this;
    }

    public B inputPrompt(String prompt) {
        target.setInputPrompt(prompt);
        return (B) this;
    }
    
    public B onTextChange(Consumer<String> listener) {
        target.addTextChangeListener(e -> listener.accept(e.getText()));
        return (B) this;
    }

}
