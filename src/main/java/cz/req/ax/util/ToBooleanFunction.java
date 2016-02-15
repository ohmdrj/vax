package cz.req.ax.util;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 15.2.2016
 */
@FunctionalInterface
public interface ToBooleanFunction<T> {

    boolean applyAsBoolean(T value);

}
