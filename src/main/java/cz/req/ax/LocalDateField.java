package cz.req.ax;

import com.vaadin.data.Property;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.DateField;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 10.4.2015
 */
public class LocalDateField extends CustomField<LocalDate> {

    private final DateField field;

    public LocalDateField() {
        field = new DateField();
        field.addValueChangeListener(e -> {
            Date value = field.getValue();
            if (value != null) {
                setValue(LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault()).toLocalDate());
            } else {
                setValue(null);
            }
        });
        field.setImmediate(true);
        field.setParseErrorMessage("Neplatný formát data.");
        field.setDateOutOfRangeMessage("Datum je mimo povolený rozsah.");

        setDateFormat("dd.MM.yyyy");
        addStyleName("local-date-field");
        setSizeUndefined();
    }

    public LocalDateField(String caption) {
        this();
        setCaption(caption);
    }

    public LocalDateField(Property dataSource) {
        this();
        setPropertyDataSource(dataSource);
    }

    public LocalDateField(String caption, Property dataSource) {
        this();
        setCaption(caption);
        setPropertyDataSource(dataSource);
    }

    public LocalDateField(String caption, LocalDate value) {
        this();
        setCaption(caption);
        setValue(value);
    }

    @Override
    public Class<? extends LocalDate> getType() {
        return LocalDate.class;
    }

    @Override
    protected Component initContent() {
        return field;
    }

    @Override
    protected void setInternalValue(LocalDate newValue) {
        super.setInternalValue(newValue);
        if (newValue != null) {
            field.setValue(Date.from(newValue.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        } else {
            field.setValue(null);
        }
    }

    public void setDateFormat(String dateFormat) {
        field.setDateFormat(dateFormat);
    }

    public void setResolution(Resolution resolution) {
        field.setResolution(resolution);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        field.setReadOnly(readOnly);
        super.setReadOnly(readOnly);
    }

    @Override
    public void setEnabled(boolean enabled) {
        field.setEnabled(enabled);
        super.setEnabled(enabled);
    }

}
