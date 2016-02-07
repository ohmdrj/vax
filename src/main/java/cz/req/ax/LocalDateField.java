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
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 10.4.2015
 */
public class LocalDateField extends CustomField<LocalDate> {

    private static final String[] FALLBACK_FORMATS = new String[] {"ddMMyyyy", "ddMMyy"};

    private final DateField field;

    public LocalDateField() {
        field = new DateField() {
            @Override
            protected Date handleUnparsableDateString(String dateString) throws Converter.ConversionException {
                for (String format: FALLBACK_FORMATS) {
                    try {
                        if (dateString.length() == format.length()) {
                            return new SimpleDateFormat(format).parse(dateString);
                        }
                    } catch (ParseException ignored) {
                    }
                }
                return super.handleUnparsableDateString(dateString);
            }
        };
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
