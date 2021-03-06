package cz.req.ax;

import com.google.common.base.Strings;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.AbstractBeanContainer;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Function;


/**
 * @deprecated use {@link cz.req.ax.data.AxBinder} + {@link FormLayout}
 */
@Deprecated
public class AxForm<T> extends CustomComponent {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static <T> AxForm<T> init(Class<T> beanType) {
        Assert.notNull(beanType);
        try {
            return init(beanType.newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> AxForm<T> init(T beanValue) {
        Assert.notNull(beanValue);
        return new AxForm<>(new BeanItem<>(beanValue));
    }

    public static <T> AxForm<T> init(BeanItem<T> beanItem) {
        Assert.notNull(beanItem);
        return new AxForm<>(beanItem);
    }

    Class<T> beanClass;
    AxBinder<T> binder;
    Consumer<T> error;
    List<AxField> fields = new ArrayList<>();
    HorizontalLayout buttonBar;
    AxFormValidator<T> formValidator;
    Label formErrorLabel;
    String captionSuffix;

    private AxForm(BeanItem<T> beanItem) {
        beanClass = (Class<T>) beanItem.getBean().getClass();
        binder = AxBinder.init(beanClass);
        binder.setItemDataSource(beanItem);
        formErrorLabel = new Label();
        formErrorLabel.setVisible(false);
        formErrorLabel.addStyleName("form-error");
        setSizeUndefined();
        addStyleName("item-form");
        addComponent(formErrorLabel);
        handlePreCommit(event -> fields.forEach(field -> field.field.setRequiredError(field.requiredMessage)));
        handlePreCommit(event -> {
            formErrorLabel.setVisible(false);
            if (formValidator != null) {
                try {
                    formValidator.validate(this);
                } catch (Validator.InvalidValueException e) {
                    formErrorLabel.setValue(e.getMessage());
                    formErrorLabel.setVisible(true);
                    throw e;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AxForm<T> error(Consumer<T> error) {
        this.error = error;
        return this;
    }

    public AxForm<T> layoutCss() {
        CssLayout layout = new CssLayout();
        setCompositionRoot(layout);
        return this;
    }

    public void setCaptionSuffix(String captionSuffix) {
        this.captionSuffix = captionSuffix;
    }

    public void setFieldFactory(FieldGroupFieldFactory fieldFactory) {
        binder.setFieldFactory(fieldFactory);
    }

    public BeanFieldGroup<T> getFieldGroup() {
        return binder;
    }

    public AxBinder<T> getBinder() {
        return binder;
    }

    public AbstractLayout getRootLayout() {
        if (getCompositionRoot() instanceof AbstractLayout) {
            return (AbstractLayout) getCompositionRoot();
        }
        FormLayout layout = new FormLayout();
        layout.setSizeUndefined();
        layout.setMargin(false);
        setCompositionRoot(layout);
        return layout;
    }

    public AxForm<T> validator(AxFormValidator<T> validator) {
        this.formValidator = validator;
        return this;
    }

    public void handlePreCommit(Consumer<FieldGroup.CommitEvent> handler) {
        binder.addCommitHandler(new FieldGroup.CommitHandler() {
            @Override
            public void preCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
                handler.accept(commitEvent);
            }

            @Override
            public void postCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
            }
        });
    }

    public void handlePostCommit(Consumer<FieldGroup.CommitEvent> handler) {
        binder.addCommitHandler(new FieldGroup.CommitHandler() {
            @Override
            public void preCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
            }

            @Override
            public void postCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
                handler.accept(commitEvent);
            }
        });
    }

    public void addComponent(Component component) {
//        if (component instanceof Field) {
//            ((Field)component).getCaption()
//        }
        getRootLayout().addComponent(component);
    }

    public Item getItem() {
        return binder.getItemDataSource();
    }

    public Integer getValueId() {
        return ObjectIdentity.id(getValue());
    }

    public T getValue() {
        Item itemValue = binder.getItemDataSource();
        if (itemValue == null) {
            return null;
        } else if (itemValue instanceof BeanItem) {
            return ((BeanItem<T>) itemValue).getBean();
        } else {
            throw new IllegalArgumentException("Unknown item class " + itemValue);
        }
    }

    public void setItem(BeanItem<T> beanItem) {
        if (beanItem instanceof BeanItem) {
            binder.setItemDataSource(beanItem);
            if (!binder.isEnabled()) binder.setEnabled(true);
        } else {
            binder.setEnabled(false);
        }
    }

    /*public void setItem(EntityItem<T> beanItyem) {
        fieldGroup.setItemDataSource(beanItem);
    }*/

    public void setValue(T itemValue) {
        if (itemValue == null) {
            binder.setItemDataSource((T) null);
        } else {
            binder.setItemDataSource(new BeanItem<T>((T) itemValue));
        }
    }

    protected void checkItemNest(String property) {
        /*if (fieldGroup.getItemDataSource().getItemProperty(property) == null) {
            ((BeanItem) fieldGroup.getItemDataSource()).addNestedProperty(property);
        }*/
    }

    public AxForm<T> fieldFactory(FieldGroupFieldFactory fieldFactory) {
        binder.setFieldFactory(fieldFactory);
        return this;
    }

    private <F extends Field> AxField<F> addField(AxField<F> field) {
        F f = field.field();
        f.setSizeUndefined();
        if (f instanceof TextField) {
            ((TextField) f).setNullRepresentation("");
        }
        if (!Strings.isNullOrEmpty(captionSuffix)) {
            f.setCaption(Strings.nullToEmpty(f.getCaption()) + captionSuffix);
        }
        fields.add(field);
        addComponent(f);
        return field;
    }

    public AxForm<T> addCaption(String caption) {
        Label section = new Label(caption);
        section.addStyleName("colored");
        //section.addStyleName("h2");
        addComponent(section);
        return this;
    }

    //TODO Review
    @Deprecated
    public AxForm<T> addButton(String caption, Button.ClickListener listener) {
        getButtonBar().addComponent(new Button(caption, listener));
        return this;
    }

    public AxForm<T> addAction(String caption, Runnable run) {
        addComponent(new AxAction().caption(caption).run(run).button());
        return this;
    }

    public AbstractField<String> addLabel(String property) {
        return addLabel(null, property);
    }

    public AbstractField<String> addLabel(String caption, String property) {
        checkItemNest(property);
        LabelField field = new LabelField();
        field.setConverter(new StringToConverter());
        binder.bind(field, property);
        if (caption != null) {
            Label label = new Label(caption);
            label.addStyleName("caption");
            label.setSizeUndefined();
            addComponent(label);
        }
        addComponent(field);
        return field;
    }

    public <F extends Field> AxField<F> addField(F field, String property) {
        binder.bind(field, property);
        return addField(new AxField<>(field));
    }

    public AxField<? extends Field> addField(String caption, String property) {
        Field<?> field = binder.buildAndBind(caption, property);
        return addField(new AxField<>(field));
    }

    public <T extends AbstractField> AxField<T> addField(String caption, String property, Class<T> type) {
        T field = binder.buildAndBind(caption, property, type);
        return addField(new AxField<>(field));
    }

    public T commit() {
        return binder.commitAndGet();
    }

    public AbstractLayout getButtonBar() {
        if (buttonBar == null) {
            buttonBar = addHorizontalLayout();
            buttonBar.setSpacing(true);
        }
        return buttonBar;
    }

    public HorizontalLayout addHorizontalLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        addComponent(layout);
        return layout;
    }

    public Window getWindow() {
        Window window = new Window(getValue().getClass().getSimpleName());
        //window.addStyleName("light");
        //window.setResizable(false);
        window.setContent(this);
        window.center();
        return window;
    }

    public AxField<PasswordField> addPassword(String caption, String property) {
        PasswordField field = new PasswordField(caption);
        field.setNullRepresentation("");
        binder.bind(field, property);
        return addField(new AxField<>(field));
    }

    /**
     * @deprecated duplicates {@link #addRichText(String, String)}
     */
    @Deprecated
    public AxField<RichTextArea> addRichtext(String caption, String property) {
        RichTextArea field = new RichTextArea(caption);
        binder.bind(field, property);
        return addField(new AxField<>(field));
    }

    public <X> AxField<AxComboBox> addCombo(String caption, String property, AbstractBeanContainer<?, X> container,
                                            Function<X, String> display) {
        AxComboBox<X> combo = new AxComboBox<>(caption, container, display);
        if (container instanceof BeanContainer && display != null) {
            combo.setConverter(new AxConverter(container));
        }
        combo.setPageLength(0);
        combo.setImmediate(true);
        binder.bind(combo, property);
        return addField(new AxField<>(combo));
    }

    public AxField<ComboBox> addCombo(String caption, String property, AbstractBeanContainer container,
                                      String display) {
        AxComboBox combo = new AxComboBox(caption, container, display);
        //TODO Burianek Tlacitko na odstraneni hodnoty z Combo (wrap, styl component-group)
        if (container instanceof BeanContainer && display != null) {
            combo.setConverter(new AxConverter(container));
        }
        combo.setPageLength(0);
        combo.setImmediate(true);
        binder.bind(combo, property);
        return addField(new AxField<>(combo));
    }

    public <T extends Enum<T>> AxField<ComboBox> addCombo(String caption, String property, Class<T> enumClass) {
        BeanItemContainer<T> container = new BeanItemContainer<>(enumClass);
        container.addAll(EnumSet.allOf(enumClass));
        ComboBox combo = new ComboBox(caption, container);
        //combo.setItemCaptionPropertyId(display);
        combo.setImmediate(true);
        binder.bind(combo, property);
        return addField(new AxField<>(combo));
    }

    public AxTableField addTable(String property, InitTableForm init) {
        try {
            Type genericType = beanClass.getDeclaredField(property).getGenericType();
            Type[] arguments = ((ParameterizedType) genericType).getActualTypeArguments();
            Class clazz = Class.forName(arguments[0].getTypeName());

            AxTableField field = new AxTableField(clazz, init);
            return field;
        } catch (Exception ex) {
            throw new RuntimeException("Cannot read class from generic type for property " + property, ex);
        }
//        fieldGroup.bind(field, property);
//        addComponent(field);
    }

    public AxField<RichTextArea> addRichText(String caption, String property) {
        RichTextArea textArea = new RichTextArea();
        textArea.setCaption(caption);
        textArea.setNullRepresentation("");
        binder.bind(textArea, property);
        return addField(new AxField<>(textArea));
    }

    public class AxField<T extends Field> {

        private T field;
        private String requiredMessage;

        public AxField(T field) {
            this.field = field;
        }

        public T field() {
            return field;
        }

        public AxField<T> validator(AxValidator<T> validator) {
            field.addValidator(value -> {
                validator.validate(field, value);
            });
            return this;
        }

        public AxField<T> validator(Validator validator) {
            field.addValidator(validator);
            return this;
        }

        public AxField<T> required() {
            field.setRequired(true);
            requiredMessage = "Povinné pole není vyplněno";
            return this;
        }

        public AxField<T> required(String message) {
            field.setRequired(true);
            requiredMessage = message;
            return this;
        }

        /*public AxField<T> required(boolean required) {
            field.setRequired(required);
            return this;
        }*/

        public AxField<T> enabled(boolean enabled) {
            field.setEnabled(enabled);
            return this;
        }

        public AxField<T> visible(boolean visible) {
            field.setVisible(visible);
            return this;
        }

        public AxField<T> style(String style) {
            field.setStyleName(style);
            return this;
        }
    }

    public interface AxFormValidator<T> {

        void validate(AxForm<T> form) throws Validator.InvalidValueException;

    }

    public interface AxValidator<T extends Field> {

        void validate(T field, Object value) throws Validator.InvalidValueException;

    }

    static class AxConverter<T> implements Converter<Integer, T> {

        AbstractBeanContainer<?, T> container;

        public AxConverter(AbstractBeanContainer<?, T> container) {
            this.container = container;
            if (container instanceof Refresh) {
                ((Refresh) container).refresh();
            }
        }

        @Override
        public T convertToModel(Integer value, Class<? extends T> targetType, Locale locale) throws ConversionException {
            if (value == null) return null;
            BeanItem<T> item = container.getItem(value);
            return item == null ? null : item.getBean();
        }

        @Override
        public Integer convertToPresentation(T value, Class<? extends Integer> targetType, Locale locale) throws ConversionException {
            if (value == null) return null;
            return ObjectIdentity.id(value);
        }

        @Override
        public Class<Integer> getPresentationType() {
            return Integer.class;
        }

        @Override
        public Class<T> getModelType() {
            return (Class<T>) container.getBeanType();
        }
    }


}
