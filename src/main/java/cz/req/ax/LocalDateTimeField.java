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
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 10.4.2015
 */
public class LocalDateTimeField extends CustomField<LocalDateTime> {

    private final LocalDateField dateField;
    private final LocalTimeField timeField;

    public LocalDateTimeField() {
        dateField = new LocalDateField();
        dateField.addValueChangeListener(e -> fireValueChange(false));

        timeField = new LocalTimeField();
        timeField.addValueChangeListener(e -> fireValueChange(false));

        addStyleName("local-date-time-field");
        setSizeUndefined();
    }

    public LocalDateTimeField(String caption) {
        this();
        setCaption(caption);
    }

    public LocalDateTimeField(Property dataSource) {
        this();
        setPropertyDataSource(dataSource);
    }

    public LocalDateTimeField(String caption, Property dataSource) {
        this();
        setCaption(caption);
        setPropertyDataSource(dataSource);
    }

    public LocalDateTimeField(String caption, LocalDateTime value) {
        this();
        setCaption(caption);
        setValue(value);
    }

    @Override
    protected Component initContent() {
        return new CssLayout(dateField, timeField);
    }

    @Override
    public Class<? extends LocalDateTime> getType() {
        return LocalDateTime.class;
    }

    @Override
    protected LocalDateTime getInternalValue() {
        LocalDate date = dateField.getValue();
        LocalTime time = timeField.getValue();

        if (date != null && time != null) {
            return date.atTime(time);
        } else {
            return null;
        }
    }

    @Override
    protected void setInternalValue(LocalDateTime newValue) {
        if (newValue != null) {
            dateField.setValue(newValue.toLocalDate());
            timeField.setValue(newValue.toLocalTime());
        } else {
            dateField.setValue(null);
            timeField.setValue(null);
        }
    }

    @Override
    public void validate() throws Validator.InvalidValueException {
        dateField.validate();
        timeField.validate();
        super.validate();
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        dateField.setReadOnly(readOnly);
        timeField.setReadOnly(readOnly);
        super.setReadOnly(readOnly);
    }

    @Override
    public void setEnabled(boolean enabled) {
        dateField.setEnabled(enabled);
        timeField.setEnabled(enabled);
        super.setEnabled(enabled);
    }

}
