package cz.req.ax.builders;

import com.vaadin.data.util.AbstractBeanContainer;
import cz.req.ax.data.AxBeanItemContainer;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 18.2.2016
 */
public class AxBeanItemContainerBuilder<BEAN> extends AxContainerBuilder<BEAN, BEAN, AxBeanItemContainer<BEAN>> {

    public AxBeanItemContainerBuilder(Class<BEAN> beanType) {
        super(beanType, beanType);
    }

    public AxBeanContainerBuilder<Integer, BEAN> integerId() {
        return idType(Integer.class);
    }

    public AxBeanContainerBuilder<Long, BEAN> longId() {
        return idType(Long.class);
    }

    public AxBeanContainerBuilder<String, BEAN> stringId() {
        return idType(String.class);
    }

    public AxBeanContainerBuilder<Integer, BEAN> integerId(AbstractBeanContainer.BeanIdResolver<Integer, BEAN> idResolver) {
        return integerId().idResolver(idResolver);
    }

    public AxBeanContainerBuilder<Long, BEAN> longId(AbstractBeanContainer.BeanIdResolver<Long, BEAN> idResolver) {
        return longId().idResolver(idResolver);
    }

    public AxBeanContainerBuilder<String, BEAN> stringId(AbstractBeanContainer.BeanIdResolver<String, BEAN> idResolver) {
        return stringId().idResolver(idResolver);
    }

    public AxBeanContainerBuilder<Integer, BEAN> integerId(String idProperty) {
        return integerId().idProperty(idProperty);
    }

    public AxBeanContainerBuilder<Long, BEAN> longId(String idProperty) {
        return longId().idProperty(idProperty);
    }

    public AxBeanContainerBuilder<String, BEAN> stringId(String idProperty) {
        return stringId().idProperty(idProperty);
    }

    public <ID> AxBeanContainerBuilder<ID, BEAN> idType(Class<ID> idType) {
        return new AxBeanContainerBuilder<>(idType, beanType);
    }

    @Override
    public AxBeanItemContainer<BEAN> get() {
        return new AxBeanItemContainer<>(beanType);
    }

}
