package cz.req.ax.builders;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.Resource;
import com.vaadin.ui.Table;
import cz.req.ax.StringToConverter;

import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 21.2.2016
 */
public class TableColumnBuilder<ID, BEAN> {
    
    private TableBuilder<ID, BEAN> tableBuilder;
    private Table table;
    private Object propertyId;

    public TableColumnBuilder(TableBuilder<ID, BEAN> tableBuilder, Object propertyId) {
        this.tableBuilder = tableBuilder;
        this.table = tableBuilder.target;
        this.propertyId = propertyId;
    }

    public TableColumnBuilder<ID, BEAN> header(String header) {
        table.setColumnHeader(propertyId, header);
        return this;
    }

    public TableColumnBuilder<ID, BEAN> footer(String footer) {
        table.setColumnFooter(propertyId, footer);
        return this;
    }

    public TableColumnBuilder<ID, BEAN> icon(Resource icon) {
        table.setColumnIcon(propertyId, icon);
        return this;
    }
    
    public TableColumnBuilder<ID, BEAN> alignment(Table.Align alignment) {
        table.setColumnAlignment(propertyId, alignment);
        return this;
    }

    public TableColumnBuilder<ID, BEAN> alignLeft() {
        return alignment(Table.Align.LEFT);
    }

    public TableColumnBuilder<ID, BEAN> alignCenter() {
        return alignment(Table.Align.CENTER);
    }

    public TableColumnBuilder<ID, BEAN> alignRight() {
        return alignment(Table.Align.RIGHT);
    }
    
    public TableColumnBuilder<ID, BEAN> collapsed(boolean collapsed) {
        table.setColumnCollapsed(propertyId, collapsed);
        return this;
    }

    public TableColumnBuilder<ID, BEAN> collapsed() {
        return collapsed(true);
    }

    public TableColumnBuilder<ID, BEAN> uncollapsed() {
        return collapsed(false);
    }

    public TableColumnBuilder<ID, BEAN> collapsible(boolean collapsible) {
        table.setColumnCollapsible(propertyId, collapsible);
        return this;
    }

    public TableColumnBuilder<ID, BEAN> collapsible() {
        return collapsible(true);
    }

    public TableColumnBuilder<ID, BEAN> uncollapsible() {
        return collapsible(false);
    }

    public TableColumnBuilder<ID, BEAN> expandRatio(float ratio) {
        table.setColumnExpandRatio(propertyId, ratio);
        return this;
    }
    
    public TableColumnBuilder<ID, BEAN> expand() {
        return expandRatio(1f);
    }
    
    public TableColumnBuilder<ID, BEAN> width(int width) {
        table.setColumnWidth(propertyId, width);
        return this;
    }
    
    public TableColumnBuilder<ID, BEAN> converter(Converter<String, ?> converter) {
        table.setConverter(propertyId, converter);
        return this;
    }

    public <V> TableColumnBuilder<ID, BEAN> format(Function<V, String> formatter) {
        return converter(new StringToConverter() {
            @Override
            public String convertToPresentation(Object value, Class<? extends String> targetType, Locale locale) throws ConversionException {
                return formatter.apply((V) value);
            }
        });
    }

//    public <V> TableColumnBuilder<ID, BEAN> icon(Function<V, FontIcon> formatter) {
//        // TODO potrebujeme style generator pro sloupce
//        return converter(new StringToConverter() {
//            @Override
//            public String convertToPresentation(Object value, Class<? extends String> targetType, Locale locale) throws ConversionException {
//                FontIcon icon = formatter.apply((V) value);
//                return icon != null ? new String(Character.toChars(icon.getCodepoint())) : null;
//            }
//        });
//    }

    public TableColumnBuilder<ID, BEAN> generator(Table.ColumnGenerator generator) {
        table.addGeneratedColumn(propertyId, generator);
        return this;
    }
    
    public TableColumnBuilder<ID, BEAN> generator(BiFunction<ID, BEAN, Object> generator) {
        return generator((source, itemId, columnId) -> generator.apply((ID) itemId,
                ((BeanItem<BEAN>) source.getItem(itemId)).getBean()));
    }

    public TableColumnBuilder<ID, BEAN> generator(Function<ID, Object> generator) {
        return generator((source, itemId, columnId) -> generator.apply((ID) itemId));
    }

    public TableColumnBuilder<ID, BEAN> column(String propertyId, String... nestedPropertyIds) {
        return tableBuilder.column(propertyId, nestedPropertyIds);
    }

    public TableBuilder<ID, BEAN> table() {
        return tableBuilder;
    }
    
    public Table get() {
        return tableBuilder.get(); // Voláme get() aby se zviditelnili sloupce
    }

}
