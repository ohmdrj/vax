package cz.req.ax.data;

import com.vaadin.data.util.BeanItemContainer;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 18.2.2016
 */
public class AxBeanItemContainer<BEAN> extends BeanItemContainer<BEAN> implements AxContainer<BEAN, BEAN> {

    private AxContainerHelper<BEAN, BEAN> helper = new AxContainerHelper<>(this);

    public AxBeanItemContainer(Class<? super BEAN> type) {
        super(type);
    }

    @Override
    public void replaceAll(Collection<? extends BEAN> beans) {
        Collection<ItemSetChangeListener> listeners = getItemSetChangeListeners();
        setItemSetChangeListeners(null);
        removeAllItems();
        addAll(beans);
        setItemSetChangeListeners(listeners);
        fireItemSetChange();
    }

    @Override
    public void sort(Object[] propertyId, boolean[] ascending) {
        if (!helper.sort(propertyId, ascending)) {
            super.sort(propertyId, ascending);
        }
    }

    @Override
    public Sort getSort() {
        return helper.getSort();
    }

    @Override
    public void setSortRequestCallback(Function<Sort, Collection<? extends BEAN>> callback) {
        helper.setSortRequestCallback(callback);
    }

    @Override
    public void setSortRequestCallback(Runnable callback) {
        helper.setSortRequestCallback(callback);
    }

    @Override
    public void setSortableContainerPropertyIds(Collection<?> propertyIds) {
        helper.setSortableContainerPropertyIds(propertyIds);
    }

    public void setSortableContainerPropertyIds(Object... propertyIds) {
        helper.setSortableContainerPropertyIds(propertyIds);
    }

    @Override
    public Collection<?> getSortableContainerPropertyIds() {
        Collection<?> ids = helper.getSortableContainerPropertyIds();
        return ids != null ? ids : super.getSortableContainerPropertyIds();
    }

}
