package cz.req.ax.builders;

import cz.req.ax.data.AxBeanItemContainer;

import java.util.EnumSet;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 19.2.2016
 */
public class EnumContainerBuilder<E extends Enum<E>> extends AxBeanItemContainerBuilder<E> {

    public EnumContainerBuilder(Class<E> beanType) {
        super(beanType);
    }

    public AxBeanItemContainer<E> ofAll() {
        return of(EnumSet.allOf(beanType));
    }

}
