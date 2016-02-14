package cz.req.ax.builders;

import cz.req.ax.LocalDateTimeField;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class DateTimeFieldBuilder extends FieldBuilder<LocalDateTime, LocalDateTimeField, DateTimeFieldBuilder> {

    public DateTimeFieldBuilder() {
        super(new LocalDateTimeField(), true);
    }

    public DateTimeFieldBuilder(LocalDateTimeField target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public DateTimeFieldBuilder(boolean useDefaults) {
        super(new LocalDateTimeField(), useDefaults);
    }

    public DateTimeFieldBuilder timeVisible(boolean visible) {
        target.setTimeVisible(visible);
        return this;
    }

    public DateTimeFieldBuilder timeVisible() {
        return timeVisible(true);
    }

    public DateTimeFieldBuilder timeHidden() {
        return timeVisible(false);
    }

    public DateTimeFieldBuilder dateTimeNow() {
        return value(LocalDateTime.now());
    }

    public DateTimeFieldBuilder dateNow() {
        return value(LocalDate.now().atStartOfDay());
    }

}
