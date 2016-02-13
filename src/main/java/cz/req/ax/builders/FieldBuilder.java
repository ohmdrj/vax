package cz.req.ax.builders;

import com.google.common.base.Strings;
import com.vaadin.data.Validator;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Field;

import java.util.function.Consumer;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class FieldBuilder<V, F extends Field<V>, B extends FieldBuilder<V, F, B>> extends ComponentBuilder<F, B> {

    public FieldBuilder(F field) {
        super(field);
    }

    public FieldBuilder(F field, boolean useDefaults) {
        super(field, useDefaults);
    }

    @Override
    protected void applyDefaults() {
        super.applyDefaults();
        requiredError("Není vyplněna hodnota.");
        conversionError("Neplatná hodnota.");
    }

    public F field() {
        return component();
    }

    public B required(boolean required) {
        component.setRequired(required);
        return (B) this;
    }

    public B required() {
        return required(true);
    }

    public B requiredError(String error) {
        component.setRequiredError(error);
        return (B) this;
    }

    public B value(V value) {
        component.setValue(value);
        return (B) this;
    }

    public B validator(Validator validator) {
        component.addValidator(validator);
        return (B) this;
    }

    public B validate(Consumer<V> validator) {
        return validator(v -> validator.accept((V) v));
    }

    public B onChange(Consumer<V> listener) {
        component.addValueChangeListener(e -> listener.accept((V) e.getProperty().getValue()));
        return (B) this;
    }

    public B converter(Class<?> datamodelType) {
        if (component instanceof AbstractField) {
            ((AbstractField) component).setConverter(datamodelType);
        }
        return (B) this;
    }

    public B converter(Converter<V, ?> converter) {
        if (component instanceof AbstractField) {
            ((AbstractField) component).setConverter(converter);
        }
        return (B) this;
    }

    public B conversionError(String error) {
        if (component instanceof AbstractField) {
            ((AbstractField) component).setConversionError(error);
        }
        return (B) this;
    }

    public B convertedValue(Object value) {
        if (component instanceof AbstractField) {
            ((AbstractField) component).setConvertedValue(value);
        }
        return (B) this;
    }

}
