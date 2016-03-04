package cz.req.ax.ui;

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

        updateTimeStyle();
        setPrimaryStyleName("ax-localdatetimefield");
        setSizeUndefined();
    }

    public LocalDateTimeField(String caption) {
        this();
        setCaption(caption);
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

        if (date != null) {
            if (time != null) {
                return date.atTime(time);
            } if (!timeField.isVisible()) {
                return date.atStartOfDay();
            }
        }
        return null;
    }

    @Override
    protected void setInternalValue(LocalDateTime newValue) {
        dateField.setValue(newValue != null ? newValue.toLocalDate() : null);
        timeField.setValue(newValue != null && timeField.isVisible() ? newValue.toLocalTime() : null);
    }

    @Override
    public void validate() throws Validator.InvalidValueException {
        dateField.validate();
        if (timeField.isVisible()) {
            timeField.validate();
        }
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

    public void setTimeVisible(boolean visible) {
        timeField.setVisible(visible);
        if (!visible) {
            timeField.setValue(null);
        }
        updateTimeStyle();
    }

    public boolean isTimeVisible() {
        return timeField.isVisible();
    }

    private void updateTimeStyle() {
        if (timeField.isVisible()) {
            addStyleName("time-visible");
            removeStyleName("time-hidden");

        } else {
            removeStyleName("time-visible");
            addStyleName("time-hidden");
        }
    }

    @Override
    public void focus() {
        dateField.focus();
    }

}
