package cz.req.ax.data;

import com.vaadin.data.Container;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 18.2.2016
 */
public interface AxContainer<ID, BEAN> extends Container,
        Container.Filterable,
        Container.Indexed,
        Container.Ordered,
        Container.Sortable,
        Container.ItemSetChangeNotifier,
        Container.PropertySetChangeNotifier {

    void addAll(Collection<? extends BEAN> beans);

    void replaceAll(Collection<? extends BEAN> beans);

    Sort getSort();

    void setSortRequestCallback(Function<Sort, Collection<? extends BEAN>> callback);

    void setSortRequestCallback(Runnable callback);

    void setSortableContainerPropertyIds(Collection<?> propertyIds);

}
