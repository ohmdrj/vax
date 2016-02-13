package cz.req.ax.builders;

import com.vaadin.data.Validator;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Field;
import cz.req.ax.AxBinder;

import java.util.function.Consumer;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class FieldBuilder<V, F extends Field<V>, B extends FieldBuilder<V, F, B>> {

    protected F field;

    public FieldBuilder(F field) {
        this.field = field;
        if (field instanceof AbstractField) {
            ((AbstractField<V>) field).setImmediate(true);
        }
    }

    public F field() {
        return field;
    }

    public B caption(String caption) {
        field.setCaption(caption);
        return (B) this;
    }

    public B value(V value) {
        field.setValue(value);
        return (B) this;
    }

    public B required() {
        return required(true);
    }

    public B required(boolean required) {
        field.setRequired(required);
        return (B) this;
    }

    public B disabled() {
        return required(true);
    }

    public B enabled(boolean enabled) {
        field.setEnabled(enabled);
        return (B) this;
    }

    public B hidden() {
        return visible(false);
    }

    public B visible(boolean visible) {
        field.setEnabled(visible);
        return (B) this;
    }

    public B style(String style) {
        field.setStyleName(style);
        return (B) this;
    }

    public B validate(Consumer<V> validator) {
        return validator(v -> validator.accept((V) v));
    }

    public B validator(Validator validator) {
        field.addValidator(validator);
        return (B) this;
    }

    public B change(Consumer<V> listener) {
        field.addValueChangeListener(e -> listener.accept((V) e.getProperty().getValue()));
        return (B) this;
    }

    public B focus() {
        field.focus();
        return (B) this;
    }

}
