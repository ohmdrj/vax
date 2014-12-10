package cz.req.ax;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Table;
import cz.thickset.utils.IdObject;

//@Component("AxTable")
//@Scope("prototype")
public class AxTable<T extends IdObject<Integer>> implements RefreshListener {

    AxBeanContainer<T> container;
    BeanEventListener<T> selectListener;
    Table table;

    public AxTable(final AxBeanContainer<T> container) {
        this.container = container;

        table = new Table(null, container);
        table.addStyleName("hack-noscroll");
        table.setWidth(100, Sizeable.Unit.PERCENTAGE);
        table.setPageLength(0);
        table.setSelectable(true);
        table.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
        table.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (selectListener == null) return;
                BeanItem<T> item = container.getItem(event);
                selectListener.beanEvent(item == null ? null : item.getBean());
            }
        });
    }

    public AxTable<T> selectListener(BeanEventListener<T> beanListener) {
        selectListener = beanListener;
        return this;
    }

    public Table getTable() {
        return table;
    }

    public Table getTableFull() {
        table.setSizeFull();
        return table;
    }

    public AxBeanContainer<T> getContainer() {
        return container;
    }

    public void refresh() {
        //TODO
        //container.refresh();
        table.refreshRowCache();
    }
}
