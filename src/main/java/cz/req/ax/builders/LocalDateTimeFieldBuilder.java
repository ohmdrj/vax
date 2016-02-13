package cz.req.ax.builders;

import cz.req.ax.AxBinder;
import cz.req.ax.LocalDateTimeField;
import cz.req.ax.LocalTimeField;

import java.time.LocalDateTime;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class LocalDateTimeFieldBuilder extends FieldBuilder<LocalDateTime, LocalDateTimeField, LocalDateTimeFieldBuilder> {

    public LocalDateTimeFieldBuilder() {
        super(new LocalDateTimeField());
    }

    public LocalDateTimeFieldBuilder(String caption) {
        super(new LocalDateTimeField(caption));
    }

    public LocalDateTimeFieldBuilder(LocalDateTimeField field) {
        super(field);
    }

    public LocalDateTimeFieldBuilder(LocalDateTimeField field, boolean useDefaults) {
        super(field, useDefaults);
    }

    public LocalDateTimeFieldBuilder(boolean useDefaults) {
        super(new LocalDateTimeField(), useDefaults);
    }

    public LocalDateTimeFieldBuilder timeVisible(boolean visible) {
        component.setTimeVisible(visible);
        return this;
    }

    public LocalDateTimeFieldBuilder timeVisible() {
        return timeVisible(true);
    }

    public LocalDateTimeFieldBuilder timeHidden() {
        return timeVisible(false);
    }

}
