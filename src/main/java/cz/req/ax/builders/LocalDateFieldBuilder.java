package cz.req.ax.builders;

import cz.req.ax.ui.LocalDateField;

import java.time.LocalDate;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class LocalDateFieldBuilder extends FieldBuilder<LocalDate, LocalDateField, LocalDateFieldBuilder> {

    public LocalDateFieldBuilder() {
        super(new LocalDateField(), true);
    }

    public LocalDateFieldBuilder(LocalDateField target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public LocalDateFieldBuilder dateNow() {
        return value(LocalDate.now());
    }

}
