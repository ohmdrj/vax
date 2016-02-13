package cz.req.ax;

import com.google.common.base.Strings;
import com.vaadin.data.*;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vaadin.ui.*;
import cz.req.ax.builders.*;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

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

    public <V, F extends Field<V>, B extends FieldBuilder<V, F, B>> FieldBuilder<V, F, B> addField(F field, Object propetyId) {
        bind(field, propetyId);
        return new FieldBuilder<>(field);
    }

    public FieldBuilder addField(Object propertyId) {
        return new FieldBuilder(buildAndBind(null, propertyId));
    }

    public TextFieldBuilder addText(Object propertyId) {
        return new TextFieldBuilder(buildAndBind(null, propertyId, TextField.class));
    }

    public PasswordFieldBuilder addPassword(Object propertyId) {
        return new PasswordFieldBuilder(buildAndBind(null, propertyId, PasswordField.class));
    }

    public TextAreaBuilder addTextArea(Object propertyId) {
        return new TextAreaBuilder(buildAndBind(null, propertyId, TextArea.class));
    }

    public RichTextAreaBuilder addRichText(Object propertyId) {
        return new RichTextAreaBuilder(buildAndBind(null, propertyId, RichTextArea.class));
    }

    public ComboBoxBuilder addCombo(Object propertyId) {
        return new ComboBoxBuilder(buildAndBind(null, propertyId, AxComboBox.class));
    }

    public OptionGroupBuilder addOption(Object propertyId) {
        return new OptionGroupBuilder(buildAndBind(null, propertyId, OptionGroup.class));
    }

    public CheckBoxBuilder addCheck(Object propertyId) {
        return new CheckBoxBuilder(buildAndBind(null, propertyId, CheckBox.class));
    }

    public LocalDateFieldBuilder addDate(Object propertyId) {
        return new LocalDateFieldBuilder(buildAndBind(null, propertyId, LocalDateField.class));
    }

    public LocalTimeFieldBuilder addTime(Object propertyId) {
        return new LocalTimeFieldBuilder(buildAndBind(null, propertyId, LocalTimeField.class));
    }

    public LocalDateTimeFieldBuilder addDateTime(Object propertyId) {
        return new LocalDateTimeFieldBuilder(buildAndBind(null, propertyId, LocalDateTimeField.class));
    }

    public AxFormLayout createForm() {
        AxFormLayout layout = new AxFormLayout();
        getFields().forEach(layout::addRow);
        return layout;
    }

    public static class CommitException extends RuntimeException {

        private CommitException(FieldGroup.CommitException origin) {
            super(origin.getMessage(), origin.getCause());
        }

    }

}
