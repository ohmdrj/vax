package cz.req.ax.builders;

import cz.req.ax.data.AxBeanItemContainer;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 18.2.2016
 */
public class AxBeanItemContainerBuilder<BEAN> extends AxContainerBuilder<BEAN, BEAN, AxBeanItemContainer<BEAN>> {

    public AxBeanItemContainerBuilder(Class<BEAN> beanType) {
        super(beanType, beanType);
    }

    public <ID> AxBeanContainerBuilder<ID, BEAN> idType(Class<ID> idType) {
        return new AxBeanContainerBuilder<>(idType, beanType);
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

    @Override
    public AxBeanItemContainer<BEAN> get() {
        return new AxBeanItemContainer<>(beanType);
    }

}
