package cz.req.ax;

import com.google.common.base.Strings;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.Field;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 13.1.2016
 */
public class AxBinder<T> extends BeanFieldGroup<T> {

    public static <T> AxBinder<T> init(Class<T> beanType) {
        return new AxBinder<>(beanType);
    }

    private AxBinder(Class<T> beanType) {
        super(beanType);
        setFieldFactory(new AxFieldFactory());
    }

    @Override
    public void commit() {
        for (Field field: getFields()) {
            if (field.isRequired() && Strings.isNullOrEmpty(field.getRequiredError())) {
                field.setRequiredError("Není vyplněna hodnota.");
            }
        }
        try {
            super.commit();
        } catch (Exception e) {
            throw new RuntimeException("Chyba při ukládání formuláře", e);
        }
    }

}
