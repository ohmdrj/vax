package cz.req.ax;

import com.vaadin.data.util.BeanItemContainer;

public class AxItemContainer<T> extends BeanItemContainer<T> {

    public static <T> AxItemContainer<T> init(Class<T> type) {
        return new AxItemContainer<>(type);
    }

    protected AxItemContainer(Class<T> type) {
        super(type);
    }

}
