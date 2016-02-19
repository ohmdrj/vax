package cz.req.ax.builders;

import cz.req.ax.AxUtils;
import cz.req.ax.data.AxContainer;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Objects;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 19.2.2016
 */
public abstract class AxContainerBuilder<ID, BEAN, CONTAINER extends AxContainer<ID, BEAN>> {

    protected Class<ID> idType;
    protected Class<BEAN> beanType;

    public AxContainerBuilder(Class<ID> idType, Class<BEAN> beanType) {
        this.idType = Objects.requireNonNull(idType);
        this.beanType = Objects.requireNonNull(beanType);
    }

    public abstract CONTAINER get();

    public CONTAINER of(Collection<? extends BEAN> collection) {
        CONTAINER container = get();
        container.addAll(collection);
        return container;
    }

    public CONTAINER of(BEAN bean, BEAN... otherBeans) {
        return of(AxUtils.concat(bean, otherBeans));
    }

}
