package cz.req.ax.data;

import com.google.common.collect.ImmutableMap;
import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.util.converter.*;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import cz.req.ax.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 12.1.2016
 */
public class AxFieldFactory extends DefaultFieldGroupFieldFactory {

    private static final Map<Class<? extends Number>, Class<? extends Converter>> NUMBER_CONVERTERS =
            new ImmutableMap.Builder<Class<? extends Number>, Class<? extends Converter>>()
                    .put(Integer.class, StringToIntegerConverter.class)
                    .put(Long.class, StringToLongConverter.class)
                    .put(Float.class, StringToFloatConverter.class)
                    .put(Double.class, StringToDoubleConverter.class)
                    .put(BigDecimal.class, StringToBigDecimalConverter.class)
                    .build();

    @Override
    public <T extends Field> T createField(Class<?> propertyType, Class<T> fieldType) {
        T field = instantiateField(propertyType, fieldType);
        if (field != null) {
            configureField(propertyType, field);
        }
        return field;
    }

    public <T extends Field> T createField(Object bean, Class<?> propertyType, Object propertyId, Class<T> fieldType) {
        T field = instantiateField(bean, propertyType, propertyId, fieldType);
        if (field != null) {
            configureField(bean, propertyType, propertyId, field);
        }
        return field;
    }

    protected <T extends Field> T instantiateField(Object bean, Class<?> propertyType, Object propertyId, Class<T> fieldType) {
        return instantiateField(propertyType, fieldType);
    }

    protected <T extends Field> T instantiateField(Class<?> propertyType, Class<T> fieldType) {
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

    protected <T extends Field> void configureField(Object bean, Class<?> propertyType, Object propertyId, T field) {
        configureField(propertyType, field);
    }

    protected <T extends Field> void configureField(Class<?> propertyType, T field) {
        if (field instanceof AbstractComponent) {
            ((AbstractComponent) field).setImmediate(true);
        }
        if (field instanceof AbstractField) {
            ((AbstractField) field).setConversionError("Neplatná hodnota.");
        }
        if (field instanceof AbstractTextField) {
            ((AbstractTextField) field).setNullSettingAllowed(false);
            ((AbstractTextField) field).setNullRepresentation("");
        }
        if (field instanceof RichTextArea) {
            ((RichTextArea) field).setNullSettingAllowed(false);
            ((RichTextArea) field).setNullRepresentation("");
        }
        if (field instanceof AbstractSelect) {
            ((AbstractSelect) field).setNullSelectionAllowed(true);
            ((AbstractSelect) field).setNewItemsAllowed(false);
        }
        if (field instanceof AbstractSelect.Filtering) {
            ((AbstractSelect.Filtering) field).setFilteringMode(FilteringMode.CONTAINS);
        }
        if (NUMBER_CONVERTERS.containsKey(propertyType)) {
            if (field instanceof AbstractTextField || field instanceof LabelField) {
                ((AbstractField) field).setConverter(NUMBER_CONVERTERS.get(propertyType));
            }
        }
    }

}
