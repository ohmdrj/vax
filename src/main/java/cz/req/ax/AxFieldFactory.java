package cz.req.ax;

import com.google.common.collect.ImmutableMap;
import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.util.converter.*;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

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
    public <T extends Field> T createField(Class<?> type, Class<T> fieldType) {
        if (LocalDate.class.equals(type) && fieldType.isAssignableFrom(LocalDateField.class)) {
            return (T) new LocalDateField();
        }
        if (LocalTime.class.equals(type) && fieldType.isAssignableFrom(LocalTimeField.class)) {
            return (T) new LocalTimeField();
        }
        if (LocalDateTime.class.equals(type) && fieldType.isAssignableFrom(LocalDateTimeField.class)) {
            return (T) new LocalDateTimeField();
        }
        if (NUMBER_CONVERTERS.containsKey(type) && fieldType.isAssignableFrom(TextField.class)) {
            TextField field = createAbstractTextField(TextField.class);
            field.setConverter(NUMBER_CONVERTERS.get(type));
            return (T) field;
        }
        return super.createField(type, fieldType);
    }

    @Override
    protected <T extends AbstractTextField> T createAbstractTextField(Class<T> fieldType) {
        T field = super.createAbstractTextField(fieldType);
        field.setNullRepresentation("");
        field.setConversionError("Neplatná hodnota.");
        return field;
    }

}
