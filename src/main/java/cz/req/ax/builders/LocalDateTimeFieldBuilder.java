package cz.req.ax.builders;

import cz.req.ax.ui.LocalDateTimeField;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class LocalDateTimeFieldBuilder extends FieldBuilder<LocalDateTime, LocalDateTimeField, LocalDateTimeFieldBuilder> {

    public LocalDateTimeFieldBuilder() {
        super(new LocalDateTimeField(), true);
    }

    public LocalDateTimeFieldBuilder(LocalDateTimeField target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public LocalDateTimeFieldBuilder(boolean useDefaults) {
        super(new LocalDateTimeField(), useDefaults);
    }

    public LocalDateTimeFieldBuilder timeVisible(boolean visible) {
        target.setTimeVisible(visible);
        return this;
    }

    public LocalDateTimeFieldBuilder timeVisible() {
        return timeVisible(true);
    }

    public LocalDateTimeFieldBuilder timeHidden() {
        return timeVisible(false);
    }

    public LocalDateTimeFieldBuilder dateTimeNow() {
        return value(LocalDateTime.now());
    }

    public LocalDateTimeFieldBuilder dateNow() {
        return value(LocalDate.now().atStartOfDay());
    }

}
