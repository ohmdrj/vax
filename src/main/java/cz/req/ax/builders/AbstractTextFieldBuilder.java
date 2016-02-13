package cz.req.ax.builders;

import com.vaadin.ui.AbstractTextField;
import cz.req.ax.AxBinder;

import java.util.function.Consumer;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class AbstractTextFieldBuilder<F extends AbstractTextField, B extends AbstractTextFieldBuilder<F, B>> 
        extends FieldBuilder<String, F, AbstractTextFieldBuilder<F, B>> {

    public AbstractTextFieldBuilder(F field) {
        super(field);
    }

    public AbstractTextFieldBuilder(F field, boolean useDefaults) {
        super(field, useDefaults);
    }

    @Override
    protected void applyDefaults() {
        super.applyDefaults();
        nullProhibited();
        nullRepresentation("");
    }

    public B nullAllowed(boolean allowed) {
        component.setNullSettingAllowed(allowed);
        return (B) this;
    }
    
    public B nullAllowed() {
        return nullAllowed(true);
    }

    public B nullProhibited() {
        return nullAllowed(false);
    }

    public B nullRepresentation(String representation) {
        component.setNullRepresentation(representation);
        return (B) this;
    }
    
    public B maxLength(int maxLength) {
        component.setMaxLength(maxLength);
        return (B) this;
    }

    public B columns(int columns) {
        component.setColumns(columns);
        return (B) this;
    }

    public B maxLength(Integer length) {
        component.setMaxLength(length);
        return (B) this;
    }

    public B inputPrompt(String prompt) {
        component.setInputPrompt(prompt);
        return (B) this;
    }
    
    public B onTextChange(Consumer<String> listener) {
        component.addTextChangeListener(e -> listener.accept(e.getText()));
        return (B) this;
    }

}
