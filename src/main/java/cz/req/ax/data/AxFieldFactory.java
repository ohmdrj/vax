package cz.req.ax.data;

import com.google.common.collect.ImmutableSet;
import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Field;
import cz.req.ax.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 12.1.2016
 */
public class AxFieldFactory extends DefaultFieldGroupFieldFactory implements BeanFieldFactory, BeanFieldConfigurer {

    private static final Set<Class<?>> TO_STRING_CONVERTIBLE_TYPES = ImmutableSet.of(Integer.class, Long.class,
            Float.class, Double.class, BigDecimal.class);

    @Override
    public <T extends Field> T createField(Object bean, Class<?> propertyType, Object propertyId, Class<T> fieldType) {
        return createField(propertyType, fieldType);
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

    @Override
    public <T extends Field> void configureField(Object bean, Class<?> propertyType, Object propertyId, T field) {
        Ax.defaults(field);
        AxUtils.appendCaptionSuffix(field, Ax.defaults().getCaptionSuffix());

        if (field instanceof AbstractField) {
            ((AbstractField) field).setValidationVisible(false); // Zapneme až při prvním commitu v AxBinder
        }

        if (TO_STRING_CONVERTIBLE_TYPES.contains(propertyType)) {
            if (field instanceof AbstractTextField || field instanceof LabelField) {
                ((AbstractField) field).setConverter(propertyType);
            }
        }
    }

}
