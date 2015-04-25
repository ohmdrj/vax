package cz.req.ax;

import com.vaadin.data.util.BeanContainer;

public class AxBeanContainer<T extends IdObject<Integer>> extends BeanContainer<Integer, T> {

    Class<T> type;

    public static <T extends IdObject<Integer>> AxBeanContainer<T> init(Class<T> type) {
        return new AxBeanContainer<>(type);
    }

    protected AxBeanContainer(Class<T> type) {
        super(type);
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }

    public T newBean() {
        try {
            return type.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Chyba newInstance typu "+type,e);
        }
    }

    public Integer delete(Object object) {
        Integer id;
        if (object instanceof Integer) {
            id = ((Integer) object);
        } else if (object instanceof IdObject) {
            id = ((IdEntity) object).getId();
        } else {
            throw new IllegalArgumentException();
        }
        super.removeItem(id);
        return id;
    }

}
