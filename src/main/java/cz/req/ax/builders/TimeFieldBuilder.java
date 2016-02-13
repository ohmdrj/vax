package cz.req.ax.builders;

import cz.req.ax.LocalTimeField;

import java.time.LocalTime;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class TimeFieldBuilder extends FieldBuilder<LocalTime, LocalTimeField, TimeFieldBuilder> {

    public TimeFieldBuilder() {
        super(new LocalTimeField(), true);
    }

    public TimeFieldBuilder(LocalTimeField target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public TimeFieldBuilder timeNow() {
        return value(LocalTime.now());
    }

}
