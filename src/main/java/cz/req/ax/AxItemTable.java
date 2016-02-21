package cz.req.ax;

import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractBeanContainer;

/**
 * @deprecated use {@link Ax#table(AbstractBeanContainer)}
 */
@Deprecated
public class AxItemTable<T> extends AxTable<T> {

    AxItemContainer<T> container;

    public static <T> AxItemTable<T> init(AxItemContainer<T> type) {
        return new AxItemTable<>(type);
    }

    public AxItemTable(final AxItemContainer<T> container) {
        this.container = container;
        getTable().addValueChangeListener(event -> {
            if (selectListener == null) return;
            if (event == null || event.getProperty() == null) {
                selectListener.beanEvent(null);
            } else {
                selectListener.beanEvent((T) event.getProperty().getValue());
            }
        });
        getTable().addItemClickListener(event -> {
            if (clickListener != null) {
                clickListener.beanEvent((T) event.getItemId());
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
