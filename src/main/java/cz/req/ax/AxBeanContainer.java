package cz.req.ax;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import cz.thickset.utils.IdObject;

public class AxBeanContainer<T extends IdObject<Integer>> extends BeanContainer<Integer, T> {

    public static <T extends IdObject<Integer>> AxBeanContainer<T> init(Class<T> type) {
        return new AxBeanContainer<>(type);
    }

    protected AxBeanContainer(Class<T> type) {
        super(type);
    }

    @Override
    public Class<?> getType(Object propertyId) {
        return super.getType(propertyId);
    }

    public BeanItem<T> getItem(Property.ValueChangeEvent event) {
        if (event == null || event.getProperty() == null || event.getProperty().getValue() == null) {
            return null;
        }
        return super.getItem(event.getProperty().getValue());
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
