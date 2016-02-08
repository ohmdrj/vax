package cz.req.ax;

import com.google.common.base.Strings;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

    private void commitUnchecked() {
        try {
            commit();
        } catch (FieldGroup.CommitException e) {
            throw new CommitException(e);
        }
    }

    public T commitAndGet() {
        commitUnchecked();
        return getBean();
    }

    public T commitAndMerge(JpaRepository<T, Integer> repository, Object... extraPropertyIds) {
        Integer id = ObjectIdentity.id(getBean());
        if (id != null) {
            return commitAndMerge(repository.getOne(id), extraPropertyIds);
        } else {
            return commitAndGet();
        }
    }

    public T commitAndMerge(T targetBeanValue, Object... extraPropertyIds) {
        return commitAndMerge(new BeanItem<T>(targetBeanValue), extraPropertyIds).getBean();
    }

    public BeanItem<T> commitAndMerge(BeanItem<T> targetBeanItem, Object... extraPropertyIds) {
        commitUnchecked();
        return merge(targetBeanItem, extraPropertyIds);
    }

    private BeanItem<T> merge(BeanItem<T> targetBeanItem, Object... extraPropertyIds) {
        BeanItem<T> sourceBeanItem = getItemDataSource();

        Set<Object> propertyIds = new HashSet<>();
        propertyIds.addAll(getBoundPropertyIds());
        propertyIds.addAll(Arrays.asList(extraPropertyIds));

        for (Object propertyId: propertyIds) {
            Property sourceProperty = sourceBeanItem.getItemProperty(propertyId);
            Property targetProperty =  targetBeanItem.getItemProperty(propertyId);
            targetProperty.setValue(sourceProperty.getValue());
        }

        setItemDataSource(targetBeanItem);
        return targetBeanItem;
    }

    public T getBean() {
        return getItemDataSource().getBean();
    }

    public static class CommitException extends RuntimeException {

        private CommitException(FieldGroup.CommitException origin) {
            super(origin.getMessage(), origin.getCause());
        }

    }

}
