package cz.req.ax;

import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomField;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * @deprecated moved to {@link cz.req.ax.ui.LocalDateTimeField}
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 10.4.2015
 */
@Deprecated
public class LocalDateTimeField extends cz.req.ax.ui.LocalDateTimeField {

    public LocalDateTimeField() {
    }

    public LocalDateTimeField(String caption) {
        setCaption(caption);
    }

    public LocalDateTimeField(Property dataSource) {
        setPropertyDataSource(dataSource);
    }

    public LocalDateTimeField(String caption, Property dataSource) {
        setCaption(caption);
        setPropertyDataSource(dataSource);
    }

    public LocalDateTimeField(String caption, LocalDateTime value) {
        setCaption(caption);
        setValue(value);
    }

}
