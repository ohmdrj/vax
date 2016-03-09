package cz.req.ax;


import org.springframework.data.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author by Ondřej Buriánek, burianek@marbes.cz.
 * @since 7.9.15
 */
public class ObjectIdentity {

    static final Map<Class, Method> idGetters = new LinkedHashMap<>();

    public static Integer id(Object object) {
        //TODO Burianek support Number?
        return idInteger(object);
    }

    public static Integer idInteger(Object object) {
        if (object == null) return null;
        try {
            return (Integer) getIdGetter(object.getClass()).invoke(object);
        } catch (Exception e) {
            throw new RuntimeException("Unable to resolve object ID", e);
        }
    }

    private static Method getIdGetter(Class clazz) {
        Method getter = idGetters.get(clazz);
        if (getter == null) {
            Field field = getIdField(clazz);
            try {
                getter = clazz.getMethod("get" + StringUtils.capitalize(field.getName()));
            } catch (Exception e) {
                throw new IllegalArgumentException("Unable to access getter method for ID field "
                        + field + " on class " + clazz, e);
            }
            idGetters.put(clazz, getter);
        }
        return getter;
    }

    public static Field getIdField(Class clazz) {
        Field field = findIdField(clazz);
        if (field == null) {
            throw new IllegalArgumentException("Missing field with " + Id.class + " annotation on class " + clazz);
        }
        return field;
    }

    public static Field findIdField(Class clazz) {
        return ReflectionUtils.findField(clazz, f -> f.getDeclaredAnnotation(Id.class) != null);
    }

}
