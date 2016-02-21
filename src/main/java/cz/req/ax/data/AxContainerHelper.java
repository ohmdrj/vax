package cz.req.ax.data;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 18.2.2016
 */
// Non-public!
class AxContainerHelper<ID, BEAN> {

    AxContainer<ID, BEAN> container;
    private Collection<Object> sortablePropertyIds;
    private Object[] requestedSortIds;
    private boolean[] requestedSortAsc;
    private Object sortRequestCallback;

    public AxContainerHelper(AxContainer<ID, BEAN> container) {
        this.container = container;
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

    public boolean sort(Object[] propertyId, boolean[] ascending) {
        requestedSortIds = propertyId;
        requestedSortAsc = ascending;

        if (sortRequestCallback instanceof Function) {
            container.replaceAll(((Function<Sort, Collection<? extends BEAN>>) sortRequestCallback).apply(getSort()));
            return true;
        }
        if (sortRequestCallback instanceof Runnable) {
            ((Runnable) sortRequestCallback).run();
            return true;
        }
        return false;
    }

    public void setSortRequestCallback(Object callback) {
        this.sortRequestCallback = callback;
    }

    public void setSortableContainerPropertyIds(Collection propertyIds) {
        this.sortablePropertyIds = propertyIds;
    }

    public void setSortableContainerPropertyIds(Object... propertyIds) {
        setSortableContainerPropertyIds(Arrays.asList(propertyIds));
    }

    public Collection<?> getSortableContainerPropertyIds() {
        return sortablePropertyIds;
    }

}
