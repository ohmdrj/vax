package cz.req.ax;

import cz.thickset.utils.IdObject;

public interface BeanEventListener<T> {

    public void beanEvent(T bean);

}
