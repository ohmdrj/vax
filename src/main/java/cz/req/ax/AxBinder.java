package cz.req.ax;

import com.vaadin.data.util.BeanItem;

/**
 * @deprecated moved to {@link cz.req.ax.data.AxBinder}
 */
@Deprecated
public class AxBinder<T> extends cz.req.ax.data.AxBinder<T> {

    public static <T> AxBinder<T> init(Class<T> beanType) {
        return new AxBinder<T>(beanType);
    }

    public static <T> AxBinder<T> init(T beanValue) {
        return new AxBinder<T>(beanValue);
    }

    public static <T> AxBinder<T> init(BeanItem<T> beanItem) {
        return new AxBinder<T>(beanItem);
    }

    public AxBinder(Class<T> beanType) {
        super(beanType);
    }

    public AxBinder(T bean) {
        super(bean);
    }

    public AxBinder(BeanItem<T> beanItem) {
        super(beanItem);
    }

}
