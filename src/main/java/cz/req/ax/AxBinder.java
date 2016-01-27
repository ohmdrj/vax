package cz.req.ax;

import com.google.common.base.Strings;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Field;
import org.springframework.util.Assert;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 13.1.2016
 */
public class AxBinder<T> extends BeanFieldGroup<T> {

    public static <T> AxBinder<T> init(Class<T> beanType) {
        Assert.notNull(beanType);
        try {
            return init(beanType.newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> AxBinder<T> init(T beanValue) {
        Assert.notNull(beanValue);
        return new AxBinder<>(new BeanItem<>(beanValue));
    }

    public static <T> AxBinder<T> init(BeanItem<T> beanItem) {
        Assert.notNull(beanItem);
        return new AxBinder<>(beanItem);
    }

    private AxBinder(BeanItem<T> beanItem) {
        super((Class<T>) beanItem.getBean().getClass());
        setItemDataSource(beanItem);
        setFieldFactory(new AxFieldFactory());
    }

    @Override
    public void commit() throws FieldGroup.CommitException {
        for (Field field: getFields()) {
            if (field.isRequired() && Strings.isNullOrEmpty(field.getRequiredError())) {
                field.setRequiredError("Není vyplněna hodnota.");
            }
        }
        super.commit();
    }

    public T commitValue() {
        try {
            commit();
            return getItemDataSource().getBean();
        } catch (FieldGroup.CommitException e) {
            throw new CommitException(e);
        }
    }

    public static class CommitException extends RuntimeException {

        public CommitException(FieldGroup.CommitException origin) {
            super(origin.getMessage(), origin.getCause());
        }

    }

}
