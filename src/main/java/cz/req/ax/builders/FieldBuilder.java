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

    public FieldBuilder(F target, boolean useDefaults) {
        super(target, useDefaults);
    }

    @Override
    protected void applyDefaults() {
        super.applyDefaults();
        requiredError("Není vyplněna hodnota.");
        conversionError("Neplatná hodnota.");
    }

    public B required(boolean required) {
        target.setRequired(required);
        return (B) this;
    }

    public B required() {
        return required(true);
    }

    public B requiredError(String error) {
        target.setRequiredError(error);
        return (B) this;
    }

    public B value(V value) {
        target.setValue(value);
        return (B) this;
    }

    public B validator(Validator validator) {
        target.addValidator(validator);
        return (B) this;
    }

    public B validate(Consumer<V> validator) {
        return validator(v -> validator.accept((V) v));
    }

    public B onChange(Consumer<V> listener) {
        target.addValueChangeListener(e -> listener.accept((V) e.getProperty().getValue()));
        return (B) this;
    }

    public B converter(Class<?> datamodelType) {
        if (target instanceof AbstractField) {
            ((AbstractField) target).setConverter(datamodelType);
        }
        return (B) this;
    }

    public B converter(Converter<V, ?> converter) {
        if (target instanceof AbstractField) {
            ((AbstractField) target).setConverter(converter);
        }
        return (B) this;
    }

    public B conversionError(String error) {
        if (target instanceof AbstractField) {
            ((AbstractField) target).setConversionError(error);
        }
        return (B) this;
    }

    public B convertedValue(Object value) {
        if (target instanceof AbstractField) {
            ((AbstractField) target).setConvertedValue(value);
        }
        return (B) this;
    }

}
