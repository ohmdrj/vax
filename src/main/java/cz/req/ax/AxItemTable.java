package cz.req.ax;

import com.vaadin.data.Property;

public class AxItemTable<T> extends AxTable<T> {

    AxItemContainer<T> container;

    public static <T> AxItemTable<T> init(AxItemContainer<T> type) {
        return new AxItemTable<>(type);
    }

    public AxItemTable(final AxItemContainer<T> container) {
        this.container = container;
        getTable().addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (selectListener == null) return;
                if (event == null || event.getProperty() == null) {
                    selectListener.beanEvent(null);
                } else {
                    selectListener.beanEvent((T) event.getProperty().getValue());
                }
            }
        });
    }

    @Override
    public AxItemContainer<T> getContainer() {
        return container;
    }

    public void refresh() {
        /*container.removeAllContainerFilters();
        if (containerFilter != null)
            container.addContainerFilter(containerFilter);*/
        super.refresh();
    }
}
