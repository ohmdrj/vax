package cz.req.ax.builders;

import com.vaadin.data.util.AbstractBeanContainer;
import cz.req.ax.ObjectIdentity;
import cz.req.ax.data.AxBeanContainer;

import java.beans.PropertyDescriptor;
import java.util.Objects;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 18.2.2016
 */
public class AxBeanContainerBuilder<ID, BEAN> extends AxContainerBuilder<ID, BEAN, AxBeanContainer<ID, BEAN>> {

    private AbstractBeanContainer.BeanIdResolver<ID, BEAN> idResolver;
    private String idProperty;

    public AxBeanContainerBuilder(Class<ID> idType, Class<BEAN> beanType) {
        super(idType, beanType);
        autodetectIdProperty();
    }

    public void autodetectIdProperty() {
        PropertyDescriptor descriptor = ObjectIdentity.property(beanType);
        if (descriptor != null && idType.equals(descriptor.getPropertyType())) {
            idProperty(descriptor.getName());
        }
    }

    public AxBeanContainerBuilder<ID, BEAN> idResolver(AbstractBeanContainer.BeanIdResolver<ID, BEAN> idResolver) {
        this.idResolver = Objects.requireNonNull(idResolver);
        this.idProperty = null;
        return this;
    }

    public AxBeanContainerBuilder<ID, BEAN> idProperty(String idProperty) {
        this.idProperty = Objects.requireNonNull(idProperty);
        this.idResolver = null;
        return this;
    }

    @Override
    public AxBeanContainer<ID, BEAN> get() {
        AxBeanContainer<ID, BEAN> container = new AxBeanContainer<>(idType, beanType);
        if (idResolver != null) {
            container.setBeanIdResolver(idResolver);
        } else if (idProperty != null) {
            container.setBeanIdProperty(idProperty);
        } else {
            // Jinak to vyhodi az samotny BeanContainer pri prvni operaci vlozeni
            throw new IllegalStateException("Neither ID property nor ID resolver was specified for AxContainer"
                    + " and their autodetection failed");
        }
        return container;
    }

}
