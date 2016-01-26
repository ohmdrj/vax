package cz.req.ax;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AxTable<T> implements ComponentWrapper, Refresh {

    static final String HAS_ITEM_CLICK_LISTENER = "has-item-click-listener";

    List<String> visibleColumns = new ArrayList<>();
    Container.Filter containerFilter;
    BeanEventListener<T> selectListener;
    BeanEventListener<T> clickListener;
    BeanEventListener<T> selectPredicate;
    Refresh refreshListener;
    Table table;

    public abstract Container getContainer();

    @Override
    public Component getComponent() {
        return getTable();
    }

    public Table getTable() {
        if (table == null) {
            table = new Table(null, getContainer()) {
                @Override
                public void setPageLength(int pageLength) {
                    super.setPageLength(pageLength);
                    // Pokud chceme zobrazovat jen cast (nastaveno pageLength) tak scroll musime povolit
                    if (pageLength > 0) {
                        table.addStyleName("hack-allow-v-scroll");
                    } else {
                        table.removeStyleName("hack-allow-v-scroll");
                    }
                }
            };
            table.addStyleName("hack-noscroll");
            table.setWidth(100, Sizeable.Unit.PERCENTAGE);
            table.setPageLength(0);
            table.setSelectable(true);
            table.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
        }
        return table;
    }

    public T getValue() {
        try {
            Object value = table.getValue();
            if (value == null) return null;
            if (value instanceof Integer) {
                Item item = getContainer().getItem(value);
                if (item instanceof BeanItem) {
                    return (T) ((BeanItem) item).getBean();
                } else {
                    throw new UnsupportedOperationException();
                }
            } else {
                return (T) value;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public void setValue(T value) {
        table.setValue(value);
    }

    public AxTable<T> style(String... styleClasses) {
        for (String styleClass : styleClasses) {
            getTable().addStyleName(styleClass);
        }
        return this;
    }

    //TODO Refreshable FunctionInterface???
    public AxTable<T> refresh(Refresh listener) {
        refreshListener = listener;
        return this;
    }

    public AxTable<T> selectable(boolean selectable) {
        getTable().setSelectable(selectable);
        return this;
    }

    public AxTable<T> select(BeanEventListener<T> listener) {
        selectListener = listener;
        return this;
    }

    public AxTable<T> click(BeanEventListener<T> listener) {
        clickListener = listener;
        if (clickListener != null) {
            getTable().addStyleName(HAS_ITEM_CLICK_LISTENER);
        } else {
            getTable().removeStyleName(HAS_ITEM_CLICK_LISTENER);
        }
        return this;
    }

    public AxTable<T> filter(final Predicate<T> filter) {
        return filter(new BeanFilter<T>() {

            @Override
            public boolean passFilter(T bean) {
                return filter.test(bean);
            }
        });
    }

    public AxTable<T> pageLength(int pageLength) {
        getTable().setPageLength(pageLength);
        return this;
    }

    public AxTable<T> disablePaging() {
        return pageLength(0);
    }

    public AxTable<T> filter(Container.Filter filter) {
        this.containerFilter = filter;
        return this;
    }

    public AxTable<T> columns(String... propertyIds) {
        if (propertyIds == null) {
            visibleColumns.clear();
            table.setVisibleColumns(new Object[0]);
        } else {
            for (String propertyId : propertyIds) {
                visibleColumns.add(propertyId);
            }
            table.setVisibleColumns((Object[]) propertyIds);
//            table.setColumnExpandRatio(propertyIds[0], 1f);
        }
        return this;
    }

    public ColumnFactory<T> column(String propertyId) {
        return new ColumnFactory<T>(this, propertyId);
    }

    public ColumnFactory<T> column(String propertyId, Table.ColumnGenerator columnGenerator) {
        table.addGeneratedColumn(propertyId, columnGenerator);
        return column(propertyId);
    }

    public ColumnFactory<T> column(SelectionColumn column) {
        ColumnFactory<T> factory = column(column.getColumnId(), column);
        column.setTable(getTable());
        return factory;
    }

    public AxTable<T> header(Table.ColumnHeaderMode headerMode) {
        getTable().setColumnHeaderMode(headerMode);
        return this;
    }

    public AxTable<T> done() {
        table.setVisibleColumns(visibleColumns.toArray());
        refresh();
        return this;
    }

    public static class ColumnFactory<T> {

        private AxTable<T> table;
        private String property;

        public ColumnFactory(AxTable<T> table, String property) {
            this.table = table;
            this.table.visibleColumns.add(property);
            this.property = property;
            //TODO Check nested??
            //this.table.getContainer().pro
        }

        public ColumnFactory<T> header(String header) {
            table.getTable().setColumnHeader(property, header);
            return this;
        }

        public ColumnFactory<T> width(int pixels) {
            table.getTable().setColumnWidth(property, pixels);
            return this;
        }

        public ColumnFactory<T> expand(float ratio) {
            table.getTable().setColumnExpandRatio(property, ratio);
            return this;
        }

        public ColumnFactory<T> converter(Converter<String, ?> converter) {
            table.getTable().setConverter(property, converter);
            return this;
        }

        public <MODEL> ColumnFactory<T> format(Function<MODEL, String> formatter) {
            table.getTable().setConverter(property, new StringToConverter() {
                @Override
                public String convertToPresentation(Object value, Class targetType, Locale locale) throws ConversionException {
                    return formatter.apply((MODEL) value);
                }
            });
            return this;
        }

        public ColumnFactory<T> generator(IdCellGenerator generator) {
            table.getTable().addGeneratedColumn(property, (source, itemId, columnId) ->
                    generator.generateCell(itemId, columnId));
            return this;
        }

        public ColumnFactory<T> generator(ItemCellGenerator<T> generator) {
            table.getTable().addGeneratedColumn(property, (source, itemId, columnId) -> {
                try {
                    BeanItem<T> item = (BeanItem<T>) table.getContainer().getItem(itemId);
                    return generator.generateCell(item.getBean(), itemId, columnId);
                } catch (Exception ex) {
                    return null;
                }
            });
            return this;
        }

        public ColumnFactory<T> alignRight() {
            table.getTable().setColumnAlignment(property, Table.Align.RIGHT);
            return this;
        }

        public ColumnFactory<T> alignCenter() {
            table.getTable().setColumnAlignment(property, Table.Align.CENTER);
            return this;
        }

        public ColumnFactory<T> column(String propertyId) {
            return new ColumnFactory(table, propertyId);
        }

        public ColumnFactory<T> column(String propertyId, Table.ColumnGenerator columnGenerator) {
            table.getTable().addGeneratedColumn(propertyId, columnGenerator);
            return column(propertyId);
        }

        public AxTable<T> done() {
            return table.done();
        }
    }

    public interface IdCellGenerator<T> {

        Component generateCell(T itemId, Object columnId);

    }

    public interface ItemCellGenerator<T> {

        Component generateCell(T itemObject, Object itemId, Object columnId);

    }

    public void refresh() {
        table.refreshRowCache();
        if (refreshListener != null)
            refreshListener.refresh();
    }
}
