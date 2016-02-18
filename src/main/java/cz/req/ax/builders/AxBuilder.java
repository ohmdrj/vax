package cz.req.ax.builders;

import cz.req.ax.Ax;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class AxBuilder<T, B extends AxBuilder<T, B>> {

    protected T target;

    public AxBuilder(T target, boolean useDefaults) {
        this.target = target;
        if (useDefaults) {
            Ax.defaults(target);
        }
    }

    public T get() {
        return target;
    }

}
