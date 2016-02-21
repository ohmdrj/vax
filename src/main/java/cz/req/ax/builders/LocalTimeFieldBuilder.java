package cz.req.ax.builders;

import cz.req.ax.ui.LocalTimeField;

import java.time.LocalTime;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class LocalTimeFieldBuilder extends FieldBuilder<LocalTime, LocalTimeField, LocalTimeFieldBuilder> {

    public LocalTimeFieldBuilder() {
        super(new LocalTimeField(), true);
    }

    public LocalTimeFieldBuilder(LocalTimeField target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public LocalTimeFieldBuilder timeNow() {
        return value(LocalTime.now());
    }

}
