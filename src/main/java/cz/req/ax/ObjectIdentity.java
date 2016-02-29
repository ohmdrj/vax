package cz.req.ax;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Id;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author by Ondřej Buriánek, burianek@marbes.cz.
 * @since 7.9.15
 */
public class ObjectIdentity {

    private static final Logger logger = LoggerFactory.getLogger(ObjectIdentity.class);
    static final Map<Class, Method> map = new LinkedHashMap<>();

    public static Integer id(Object object) {
        //TODO Burianek support Number?
        return idInteger(object);
    }

    public static Integer idInteger(Object object) {
        if (object == null) return null;
        try {
            return (Integer) method(object.getClass()).invoke(object);
        } catch (Exception e) {
            throw new RuntimeException("Identity exception", e);
        }
    }

    private static Method method(Class clazz) {
        Method method = map.get(clazz);
        if (method != null) return method;
        /*try {
            for (PropertyDescriptor descriptor : Introspector.getBeanInfo(clazz).getPropertyDescriptors()) {
                if (descriptor.get  )
            }
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }*/
        PropertyDescriptor descriptor = property(clazz);
        //TODO Burianek Id annotation on getter?
        if (descriptor == null)
            throw new IllegalArgumentException("Missing Id annotation at class " + clazz.getCanonicalName());
        method = descriptor.getReadMethod();
        map.put(clazz, method);
        return method;
    }

    public static PropertyDescriptor property(Class clazz) {
        for (Field field : fields(new LinkedList<>(), clazz)) {
            try {
                Id annotation = field.getDeclaredAnnotation(Id.class);
                if (annotation != null) {
                    return new PropertyDescriptor(field.getName(), clazz);
                }
            } catch (Exception e) {
                logger.warn("Failed to create PropertyDescriptor for {}: {}", field.getName(), e.getMessage());
            }
        }
        return null;
    }

    static List<Field> fields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            fields = fields(fields, type.getSuperclass());
        }
        return fields;
    }

}
