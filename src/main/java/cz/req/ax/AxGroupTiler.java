package cz.req.ax;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Label;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author by Ondřej Buriánek, burianek@marbes.cz.
 * @since 15.1.16
 */
public class AxGroupTiler<T> extends AxTiler<T> {

    Function<T, ? extends Object> grouper;
    List<Comparable> keys;

    public AxGroupTiler<T> grouper(Function<T, ? extends Object> grouper) {
        this.grouper = grouper;
        return this;
    }

    public AxGroupTiler<T> keys(Comparable... keys) {
        this.keys = Arrays.asList(keys);
        return this;
    }

    public void refresh() {
        removeAllComponents();
        if (container == null) return;
        if (container instanceof AxContainer) {
            ((AxContainer) container).refresh();
        }
        Map<Object, List<BeanItem<T>>> collect = container.getItemIds().stream().map(container::getItem)
                .collect(Collectors.groupingBy(item -> grouper.apply(item.getBean())));

        if (keys == null) {
            keys = new ArrayList<>();
            for (Object key : collect.keySet()) {
                if (key instanceof Comparable) keys.add((Comparable) key);
            }
            Collections.sort(keys);
        }
        for (Object key : keys) {
            List<BeanItem<T>> beans = collect.get(key);
            if (beans != null) {
                Label label = new Label(key.toString());
                label.setStyleName("caption");
                addComponent(label);
                for (BeanItem<T> bean : beans) {
                    createTile(bean).addStyleName("tile-" + key.toString());
                }
            }
        }
    }

}
