package cz.req.ax;

import com.vaadin.data.Property;

import java.time.LocalTime;


/**
 * @deprecated moved to {@link cz.req.ax.ui.LocalTimeField}
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 10.4.2015
 */
@Deprecated
public class LocalTimeField extends cz.req.ax.ui.LocalTimeField {

    public LocalTimeField() {
    }

    public LocalTimeField(String caption) {
        setCaption(caption);
    }

    public LocalTimeField(Property dataSource) {
        setPropertyDataSource(dataSource);
    }

    public LocalTimeField(String caption, Property dataSource) {
        setCaption(caption);
        setPropertyDataSource(dataSource);
    }

    public LocalTimeField(String caption, LocalTime value) {
        setCaption(caption);
        setValue(value);
    }

}
