package cz.req.ax.builders;

import com.vaadin.data.Validator;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Field;
import cz.req.ax.Ax;
import cz.req.ax.AxUtils;
import cz.req.ax.util.AxDefaults;
import cz.req.ax.util.ToBooleanFunction;

import java.util.function.Consumer;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class FieldBuilder<V, F extends Field<V>, B extends FieldBuilder<V, F, B>> extends ComponentBuilder<F, B> {

    public FieldBuilder(F target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public B required(boolean required) {
        target.setRequired(required);
        return (B) this;
    }

    public B required() {
        return required(true);
    }

    public B optional() {
        return required(false);
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

    public B validate(ToBooleanFunction<V> validator) {
        return validator(value -> {
            if (value != null && !validator.applyAsBoolean((V) value)) {
                throw new Validator.InvalidValueException(Ax.defaults().getInvalidValueError());
            }
        });
    }

    public B onChange(Consumer<V> listener) {
        target.addValueChangeListener(e -> listener.accept((V) e.getProperty().getValue()));
        return (B) this;
    }

    public B onChange(Runnable listener) {
        target.addValueChangeListener(e -> listener.run());
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
