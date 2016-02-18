package cz.req.ax.data;

import com.vaadin.ui.Field;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 18.2.2016
 */
public interface BeanFieldConfigurer {

    <T extends Field> void configureField(Object bean, Class<?> propertyType, Object propertyId, T field);

}
