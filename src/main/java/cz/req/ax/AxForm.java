package cz.req.ax;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.AbstractBeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.*;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.EnumSet;
import java.util.Locale;
import java.util.function.Consumer;

//TODO Refactor flow API
public class AxForm<T> extends CustomComponent {

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
    BeanFieldGroup<T> fieldGroup;

    HorizontalLayout buttonBar;

    private AxForm(BeanItem<T> beanItem) {
        beanClass = (Class<T>) beanItem.getBean().getClass();
        fieldGroup = new BeanFieldGroup<>(beanClass);
        fieldGroup.setItemDataSource(beanItem);
        fieldGroup.setBuffered(true);
        addStyleName("item-form");
    }

    public AxForm<T> layoutCss() {
        CssLayout layout = new CssLayout();
        setCompositionRoot(layout);
        return this;
    }

    public BeanFieldGroup<T> getFieldGroup() {
        return fieldGroup;
    }

    public AbstractLayout getRootLayout() {
        if (getCompositionRoot() instanceof AbstractLayout) {
            return (AbstractLayout) getCompositionRoot();
        }
        FormLayout layout = new FormLayout();
        layout.setMargin(false);
//        layoutRoot.setSpacing(false);
        setCompositionRoot(layout);
        return layout;
    }

    public void addCommitHandler(Consumer<FieldGroup.CommitEvent> handler) {
        fieldGroup.addCommitHandler(new FieldGroup.CommitHandler() {
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
        return fieldGroup.getItemDataSource();
    }

    public T getValue() {
        Item itemValue = fieldGroup.getItemDataSource();
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
            fieldGroup.setItemDataSource(beanItem);
            if (!fieldGroup.isEnabled()) fieldGroup.setEnabled(true);
        } else {
            fieldGroup.setEnabled(false);
        }
    }

    /*public void setItem(EntityItem<T> beanItyem) {
        fieldGroup.setItemDataSource(beanItem);
    }*/

    public void setValue(T itemValue) {
        if (itemValue == null) {
            fieldGroup.setItemDataSource((T) null);
        } else {
            fieldGroup.setItemDataSource(new BeanItem<T>((T) itemValue));
        }
    }

    protected void checkItemNest(String property) {
        /*if (fieldGroup.getItemDataSource().getItemProperty(property) == null) {
            ((BeanItem) fieldGroup.getItemDataSource()).addNestedProperty(property);
        }*/
    }

    public AxForm<T> addCaption(String caption) {
        Label section = new Label(caption);
        section.addStyleName("colored");
        //section.setSizeFull();
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
        fieldGroup.bind(field, property);
        if (caption != null) {
            Label label = new Label(caption);
            label.addStyleName("caption");
            label.setSizeUndefined();
            addComponent(label);
        }
        addComponent(field);
        return field;
    }

    public AxField<? extends Field> addField(String caption, String property) {
        Field<?> field = fieldGroup.buildAndBind(caption, property);
        if (field instanceof TextField) {
            ((TextField) field).setNullRepresentation("");
        }
        addComponent(field);
        return new AxField<>(field);
    }

    public AxField<? extends Field> addDate(String caption, String property) {
        Field<?> field = fieldGroup.buildAndBind(caption, property);
        if (field instanceof TextField) {
            ((TextField) field).setNullRepresentation("");
        }
        addComponent(field);
        return new AxField<>(field);
    }

    public <T extends AbstractField> AxField<T> addField(String caption, String property, Class<T> type) {
        T field = fieldGroup.buildAndBind(caption, property, type);
        addComponent(field);
        return new AxField<>(field);
    }

    public T commit() {
        try {
            fieldGroup.commit();
            return getValue();
        } catch (Exception e) {
            throw new RuntimeException("Formular nelze ulozit", e);
        }
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

    public PasswordField addPassword(String caption, String property) {
        PasswordField field = new PasswordField(caption, "");
        fieldGroup.bind(field, property);
        addComponent(field);
        return field;
    }

    public ComboBox addCombo(String caption, String property, AbstractBeanContainer container, String display) {
        ComboBox combo = new ComboBox(caption, container);
        combo.setItemCaptionPropertyId(display);
        combo.setConverter(display == null ? null : new AxConverter(container));
        combo.setImmediate(true);
        fieldGroup.bind(combo, property);
        addComponent(combo);
        return combo;
    }

    public <T extends Enum<T>> ComboBox addCombo(String caption, String property, Class<T> enumClass) {
        BeanItemContainer<T> container = new BeanItemContainer<>(enumClass);
        container.addAll(EnumSet.allOf(enumClass));
        ComboBox combo = new ComboBox(caption, container);
        //combo.setItemCaptionPropertyId(display);
        combo.setImmediate(true);
        fieldGroup.bind(combo, property);
        addComponent(combo);
        return combo;
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

    public class AxField<T extends Field> {

        private T field;

        public AxField(T field) {
            this.field = field;
        }

        public AxField<T> required() {
            required(true);
            return this;
        }

        public AxField<T> required(boolean required) {
            field.setRequired(required);
            field.setRequiredError("Není vyplněno!");
            return this;
        }

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

    static class AxConverter<T extends IdEntity> implements Converter<Integer, T> {

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
            return value == null ? null : value.getId();
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
