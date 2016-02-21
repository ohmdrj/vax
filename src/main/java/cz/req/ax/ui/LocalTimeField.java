package cz.req.ax.ui;

import com.google.common.base.Strings;
import com.google.common.primitives.Ints;
import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 10.4.2015
 */
public class LocalTimeField extends CustomField<LocalTime> {

    private final ComboBox field;

    public LocalTimeField() {
        List<String> values = new ArrayList<>();
        for (int hour = 0; hour < 24; hour++) {
            for (int minute = 0; minute < 60; minute += 15) {
                values.add(createStringValue(hour, minute));
            }
        }

        field = new ComboBox(null, values);
        field.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
        field.setNewItemsAllowed(true);
        field.addValueChangeListener(e -> fireValueChange(false));

        addStyleName("local-time-field");
        setSizeUndefined();
    }

    public LocalTimeField(String caption) {
        this();
        setCaption(caption);
    }

    @Override
    public Class<? extends LocalTime> getType() {
        return LocalTime.class;
    }

    @Override
    protected Component initContent() {
        return field;
    }

    private String createStringValue(int hour, int minute) {
        return String.format("%02d:%02d", hour, minute);
    }

    private String getStringValue() {
        return Strings.nullToEmpty((String) field.getValue()).trim();
    }

    private boolean hasStringValue() {
        return !getStringValue().isEmpty();
    }

    private LocalTime parseStringValue() {
        String input = getStringValue();
        if (input.isEmpty()) {
            return null;
        }
        String[] parts = input.split(":");
        if (parts.length != 2) {
            return null;
        }
        Integer hour = Ints.tryParse(parts[0]);
        if (hour == null || hour < 0 || hour > 23) {
            return null;
        }
        if (parts[1].length() != 2) {
            return null;
        }
        Integer minute = Ints.tryParse(parts[1]);
        if (minute == null || minute < 0 || minute > 59) {
            return null;
        }
        return LocalTime.of(hour, minute);
    }

    @Override
    protected LocalTime getInternalValue() {
        return parseStringValue();
    }

    @Override
    protected void setInternalValue(LocalTime time) {
        if (time != null) {
            String value = createStringValue(time.getHour(), time.getMinute());
            if (!field.containsId(value)) {
                field.addItem(value);
            }
            field.setValue(value);
        } else {
            field.setValue(null);
        }
    }

    @Override
    public void validate() throws Validator.InvalidValueException {
        if (hasStringValue() && parseStringValue() == null) {
            throw new Validator.InvalidValueException("Neplatný formát času.");
        }
        super.validate();
    }

    @Override
    public void focus() {
        field.focus();
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
