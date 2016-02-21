package cz.req.ax;

import com.vaadin.data.Property;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.DateField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


/**
 * @deprecated moved to {@link cz.req.ax.ui.LocalDateField}
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 10.4.2015
 */
@Deprecated
public class LocalDateField extends cz.req.ax.ui.LocalDateField {

    public LocalDateField() {
    }

    public LocalDateField(String caption) {
        setCaption(caption);
    }

    public LocalDateField(Property dataSource) {
        setPropertyDataSource(dataSource);
    }

    public LocalDateField(String caption, Property dataSource) {
        setCaption(caption);
        setPropertyDataSource(dataSource);
    }

    public LocalDateField(String caption, LocalDate value) {
        setCaption(caption);
        setValue(value);
    }

}
