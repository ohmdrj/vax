package cz.req.ax.util;

import com.vaadin.data.util.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * @author by Ondřej Buriánek, burianek@marbes.cz.
 * @since 7.8.15
 */
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    DateTimeFormatter formatter;

    public LocalDateTimeConverter() {
        this(FormatStyle.MEDIUM);
    }

    public LocalDateTimeConverter(FormatStyle style) {
        this(DateTimeFormatter.ofLocalizedDateTime(style));
    }

    public LocalDateTimeConverter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public DateTimeFormatter formatter(Locale locale) {
        if (formatter == null) {
            return DateTimeFormatter.ISO_DATE_TIME;
        }
        return formatter;
    }

    @Override
    public LocalDateTime convertToModel(String value, Class<? extends LocalDateTime> targetType, Locale locale) throws ConversionException {
        return LocalDateTime.parse(value, formatter(locale));
    }

    @Override
    public String convertToPresentation(LocalDateTime value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return formatter(locale).format(value);
    }

    @Override
    public Class<LocalDateTime> getModelType() {
        return LocalDateTime.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
