package cz.req.ax;

import com.vaadin.data.util.BeanItemContainer;

import java.util.Collection;
import java.util.List;

public class AxItemContainer<T> extends BeanItemContainer<T> {

    public static <T> AxItemContainer<T> init(Class<T> type) {
        return new AxItemContainer<>(type);
    }

    public static <T> AxItemContainer<T> init(Class<T> type, Collection<? extends T> items) {
        AxItemContainer<T> container = new AxItemContainer<>(type);
        container.addAll(items);
        return container;
    }

    protected AxItemContainer(Class<T> type) {
        super(type);
    }

    public void replaceAllItems(List<T> items) {
        removeAllItems();
        if (items != null) addAll(items);
    }
}
