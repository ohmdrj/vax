package cz.req.ax.builders;

import cz.req.ax.LocalDateField;

import java.time.LocalDate;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class DateFieldBuilder extends FieldBuilder<LocalDate, LocalDateField, DateFieldBuilder> {

    public DateFieldBuilder() {
        super(new LocalDateField(), true);
    }

    public DateFieldBuilder(LocalDateField target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public DateFieldBuilder dateNow() {
        return value(LocalDate.now());
    }

}
