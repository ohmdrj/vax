package cz.req.ax.data;

import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.ui.Field;
import cz.req.ax.AxComboBox;
import cz.req.ax.LocalDateField;
import cz.req.ax.LocalDateTimeField;
import cz.req.ax.LocalTimeField;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 12.1.2016
 */
public class AxFieldFactory extends DefaultFieldGroupFieldFactory {

    public <T extends Field> T createField(Object bean, Class<?> propertyType, Object propertyId, Class<T> fieldType) {
        return super.createField(propertyType, fieldType);
    }

    @Override
    public <T extends Field> T createField(Class<?> propertyType, Class<T> fieldType) {
        if (AxComboBox.class.equals(fieldType)) {
            AxComboBox<Object> comboBox = new AxComboBox<>();
            if (Enum.class.isAssignableFrom(fieldType)) {
                populateWithEnumData(comboBox, (Class<? extends Enum>) propertyType);
            }
            return (T) comboBox;
        }
        if (LocalDate.class.equals(propertyType) && fieldType.isAssignableFrom(LocalDateField.class)) {
            return (T) new LocalDateField();
        }
        if (LocalTime.class.equals(propertyType) && fieldType.isAssignableFrom(LocalTimeField.class)) {
            return (T) new LocalTimeField();
        }
        if (LocalDateTime.class.equals(propertyType) && fieldType.isAssignableFrom(LocalDateTimeField.class)) {
            return (T) new LocalDateTimeField();
        }
        return super.createField(propertyType, fieldType);
    }

}
