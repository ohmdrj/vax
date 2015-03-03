package cz.req.ax;

import com.vaadin.data.util.BeanItem;

public class AxBeanTable<T extends IdObject<Integer>> extends AxTable<T> {

    AxBeanContainer<T> container;

    public static <T extends IdObject<Integer>> AxBeanTable<T> init(AxBeanContainer<T> type) {
        return new AxBeanTable<>(type);
    }

    public AxBeanTable(final AxBeanContainer<T> container) {
        this.container = container;
        getTable().addValueChangeListener(event -> {
            if (selectListener == null) return;
            if (event == null || event.getProperty() == null || event.getProperty().getValue() == null) {
                selectListener.beanEvent(null);
            } else {
                BeanItem<T> item = container.getItem(event.getProperty().getValue());
                selectListener.beanEvent(item == null ? null : item.getBean());
            }
        });
    }

    @Override
    public AxBeanContainer<T> getContainer() {
        return container;
    }

    public void refresh() {
        //TODO Advocate?
        container.removeAllContainerFilters();
        if (containerFilter != null)
            container.addContainerFilter(containerFilter);
        super.refresh();
    }
}
