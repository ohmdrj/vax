package cz.req.ax;

import com.vaadin.data.util.BeanItemContainer;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * @deprecated use {@link Ax#container(Class)} or {@link cz.req.ax.data.AxBeanItemContainer}
 */
@Deprecated
public class AxItemContainer<T> extends BeanItemContainer<T> {

    private Collection<Object> sortablePropertyIds;
    private Object[] requestedSortIds;
    private boolean[] requestedSortAsc;
    private Object sortRequestCallback;

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

    public void replaceAllItems(Collection<? extends T> items) {
        removeAllItems();
        if (items != null) addAll(items);
    }

    @Override
    public void sort(Object[] propertyId, boolean[] ascending) {
        requestedSortIds = propertyId;
        requestedSortAsc = ascending;

        if (sortRequestCallback instanceof Function) {
            replaceAllItems(((Function<Sort, Collection<? extends T>>) sortRequestCallback).apply(getSort()));
        } else if (sortRequestCallback instanceof Runnable) {
            ((Runnable) sortRequestCallback).run();
        } else {
            super.sort(propertyId, ascending);
        }
    }

    public Sort getSort() {
        List<Sort.Order> orders = new ArrayList<>();
        if (requestedSortIds != null) {
            for (int i = 0; i < requestedSortIds.length; i++) {
                String propertyId = requestedSortIds[i].toString();
                Sort.Direction direction = requestedSortAsc[i] ? Sort.Direction.ASC : Sort.Direction.DESC;
                orders.add(new Sort.Order(direction, propertyId));
            }
        }
        return orders.isEmpty() ? null : new Sort(orders);
    }

    public void setSortRequestCallback(Function<Sort, Collection<? extends T>> callback) {
        sortRequestCallback = callback;
    }

    public void setSortRequestCallback(Runnable callback) {
        sortRequestCallback = callback;
    }

    public void setSortablePropertyIds(Collection propertyIds) {
        sortablePropertyIds = propertyIds;
    }

    public void setSortablePropertyIds(Object propertyId, Object... otherPropertyIds) {
        sortablePropertyIds = new ArrayList<>();
        sortablePropertyIds.add(propertyId);
        sortablePropertyIds.addAll(Arrays.asList(otherPropertyIds));
    }

    @Override
    public Collection getSortablePropertyIds() {
        return sortablePropertyIds != null ? sortablePropertyIds : super.getSortablePropertyIds();
    }
}
