package cz.req.ax.builders;

import cz.req.ax.AxBinder;
import cz.req.ax.LocalDateTimeField;

import java.time.LocalDateTime;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class LocalDateTimeFieldBuilder extends FieldBuilder<LocalDateTime, LocalDateTimeField, LocalDateTimeFieldBuilder> {

    public LocalDateTimeFieldBuilder() {
        super(new LocalDateTimeField());
    }

    public LocalDateTimeFieldBuilder(LocalDateTimeField field) {
        super(field);
    }

    public LocalDateTimeFieldBuilder hideTime() {
        return timeVisible(false);
    }

    public LocalDateTimeFieldBuilder timeVisible(boolean visible) {
        field.setTimeVisible(visible);
        return this;
    }

}
