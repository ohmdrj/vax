package cz.req.ax.builders;

import cz.req.ax.LocalDateField;
import cz.req.ax.LocalTimeField;

import java.time.LocalTime;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class LocalTimeFieldBuilder extends FieldBuilder<LocalTime, LocalTimeField, LocalTimeFieldBuilder> {

    public LocalTimeFieldBuilder() {
        super(new LocalTimeField());
    }

    public LocalTimeFieldBuilder(String caption) {
        super(new LocalTimeField(caption));
    }

    public LocalTimeFieldBuilder(LocalTimeField field) {
        super(field);
    }

    public LocalTimeFieldBuilder(LocalTimeField field, boolean useDefaults) {
        super(field, useDefaults);
    }

}
