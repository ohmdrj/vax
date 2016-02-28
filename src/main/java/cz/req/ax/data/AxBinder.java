package cz.req.ax.data;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import cz.req.ax.Ax;
import cz.req.ax.AxUtils;
import cz.req.ax.ObjectIdentity;
import cz.req.ax.builders.*;
import cz.req.ax.ui.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 13.1.2016
 */
public class AxBinder<T> extends BeanFieldGroup<T> implements LayoutFiller {

    public AxBinder(Class<T> beanType) {
        this(AxUtils.newInstance(beanType));
    }

    public AxBinder(T bean) {
        this(new BeanItem<>(Objects.requireNonNull(bean)));
    }

    public AxBinder(BeanItem<T> beanItem) {
        super((Class<T>) beanItem.getBean().getClass());
        setItemDataSource(beanItem);
        setFieldFactory(new AxFieldFactory());
    }

    public T getBean() {
        return getItemDataSource().getBean();
    }

    public <T> T getFieldValue(Class<T> propertyType, Object propertyId) {
        return (T) getFieldValue(propertyId);
    }

    public Object getFieldValue(Object propertyId) {
        Field<?> field = getField(propertyId);
        Objects.requireNonNull(field, "Property " + propertyId + " not bound");
        return field.getValue();
    }

    @Override
    public <T extends Field> T buildAndBind(String caption, Object propertyId, Class<T> fieldType) throws BindException {
        Class<?> propertyType = getPropertyType(propertyId);

        T field = build(caption, propertyType, propertyId, fieldType);
        bind(field, propertyId);

        return field;
    }

    private <T extends Field> T build(String caption, Class<?> propertyType, Object propertyId, Class<T> fieldType) throws BindException {
        T field = null;
        if (getFieldFactory() instanceof BeanFieldFactory) {
            field = ((BeanFieldFactory) getFieldFactory()).createField(getBean(), propertyType, propertyId, fieldType);
        }
        if (field == null) {
            field = getFieldFactory().createField(propertyType, fieldType);
        }
        if (field == null) {
            throw new BindException("Unable to build a field of type " + fieldType.getName()
                    + " for editing " + propertyType.getName());
        }
        if (caption != null) {
            field.setCaption(caption);
        }
        return field;
    }

    @Override
    public void bind(Field field, Object propertyId) {
        super.bind(field, propertyId);
    }

    public boolean isPropertyIdBound(Object propertyId) {
        return getBoundPropertyIds().contains(propertyId);
    }

    public boolean isBound(Field<?> field) {
        return getBoundPropertyIds().contains(getPropertyId(field));
    }

    public void setBound(Field<?> field, Object propertyId, boolean shouldBeBound) {
        if (!isBound(field) && !isPropertyIdBound(propertyId) && shouldBeBound) {
            bind(field, propertyId);
        } else if (isBound(field) && !shouldBeBound) {
            unbind(field);
        }
    }

    @Override
    protected void configureField(Field<?> field) {
        super.configureField(field);
        if (getFieldFactory() instanceof BeanFieldConfigurer) {
            Class propertyType = field.getPropertyDataSource().getType();
            Object propertyId = getPropertyId(field);
            ((BeanFieldConfigurer) getFieldFactory()).configureField(getBean(), propertyType, propertyId, field);
        }
    }

    @Override
    public void commit() throws FieldGroup.CommitException {
        // V zakladu maji vsechny fieldy vypnuto zobrazeni validacnich chyb (AxFieldFactory#configureField),
        // Viditelnost chyb zapneme az po prvnim potvrzeni formulare, aby uzivatel do te doby videl cisty
        // formular (bez chyb). Toto udelame pouze pro viditelne fieldy
        for (Field field: getFields()) {
            if (field instanceof AbstractField) {
                AbstractField abstractField = (AbstractField) field;
                if (field.isVisible()) {
                    // Netestovat zda ma jiz priznak nastaven, nektere slozitejsi komponenty to vyzaduji!
                    abstractField.setValidationVisible(true);
                }
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

    public <V, F extends Field<V>, B extends FieldBuilder<V, F, B>> FieldBuilder<V, F, B> addField(F field, Object propetyId) {
        bind(field, propetyId);
        return new FieldBuilder<>(field, false);
    }

    public <B extends FieldBuilder<Object, Field<Object>, B>> FieldBuilder<Object, Field<Object>, B> addField(Object propertyId) {
        return new FieldBuilder(buildAndBind(null, propertyId), false);
    }

    public TextFieldBuilder addTextField(Object propertyId) {
        return new TextFieldBuilder(buildAndBind(null, propertyId, TextField.class), false);
    }

    public PasswordFieldBuilder addPasswordField(Object propertyId) {
        return new PasswordFieldBuilder(buildAndBind(null, propertyId, PasswordField.class), false);
    }

    public TextAreaBuilder addTextArea(Object propertyId) {
        return new TextAreaBuilder(buildAndBind(null, propertyId, TextArea.class), false);
    }

    public RichTextAreaBuilder addRichTextArea(Object propertyId) {
        return new RichTextAreaBuilder(buildAndBind(null, propertyId, RichTextArea.class), false);
    }

    public ComboBoxBuilder addComboBox(Object propertyId) {
        return new ComboBoxBuilder(buildAndBind(null, propertyId, AxComboBox.class), false);
    }

    public OptionGroupBuilder addOptionGroup(Object propertyId) {
        return new OptionGroupBuilder(buildAndBind(null, propertyId, OptionGroup.class), false);
    }

    public CheckBoxBuilder addCheckBox(Object propertyId) {
        return new CheckBoxBuilder(buildAndBind(null, propertyId, CheckBox.class), false);
    }

    public LocalDateFieldBuilder addDateField(Object propertyId) {
        return new LocalDateFieldBuilder(buildAndBind(null, propertyId, LocalDateField.class), false);
    }

    public LocalTimeFieldBuilder addTimeField(Object propertyId) {
        return new LocalTimeFieldBuilder(buildAndBind(null, propertyId, LocalTimeField.class), false);
    }

    public LocalDateTimeFieldBuilder addDateTimeField(Object propertyId) {
        return new LocalDateTimeFieldBuilder(buildAndBind(null, propertyId, LocalDateTimeField.class), false);
    }

    @Override
    public void fillLayout(Layout layout) {
        getFields().forEach(layout::addComponent);
    }

    public FormLayoutBuilder createFormLayout() {
        return Ax.formLayout().fill(this);
    }

    public CssLayoutBuilder createCssLayout() {
        return Ax.cssLayout().fill(this);
    }

    public static class CommitException extends RuntimeException {

        private CommitException(FieldGroup.CommitException origin) {
            super(origin.getMessage(), origin.getCause());
        }

    }

}
