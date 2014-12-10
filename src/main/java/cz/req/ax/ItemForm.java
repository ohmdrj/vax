package cz.req.ax;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.Locale;

public class ItemForm<T> extends FormLayout {

    public static <T> ItemForm<T> init(Class<T> beanType) {
        try {
            return init(beanType.newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> ItemForm<T> init(T beanValue) {
        return new ItemForm<T>(new BeanItem<T>(beanValue));
    }

    public static <T> ItemForm<T> init(BeanItem<T> beanItem) {
        return new ItemForm<T>(beanItem);
    }

    FieldGroup fieldGroup;
    HorizontalLayout buttonBar;

    private ItemForm(Item itemData) {
        fieldGroup = new FieldGroup(itemData);
        fieldGroup.setBuffered(true);
        addStyleName("itemform");
        setMargin(true);
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
        fieldGroup.setItemDataSource(beanItem);
    }

    /*public void setItem(EntityItem<T> beanItyem) {
        fieldGroup.setItemDataSource(beanItem);
    }*/

    public void setValue(T itemValue) {
        if (itemValue == null) {
            fieldGroup.setItemDataSource(null);
        } else {
            fieldGroup.setItemDataSource(new BeanItem<T>((T) itemValue));
        }
    }

    public ItemForm<T> addCaption(String caption) {
        Label section = new Label(caption);
        section.addStyleName("colored");
        //section.setSizeFull();
        //section.addStyleName("h2");
        addComponent(section);
        return this;
    }

    public ItemForm<T> addButton(String caption, Button.ClickListener listener) {
        getButtonBar().addComponent(new Button(caption, listener));
        return this;
    }

    public Field<?> addField(String caption, String property) {
        Field<?> field = fieldGroup.buildAndBind(caption, property);
        if (field instanceof TextField) {
            ((TextField) field).setNullRepresentation("");
        }
        addComponent(field);
        return field;
    }

    public <T extends Field> Field<T> addField(String caption, String property, Class<T> type) {
        T field = fieldGroup.buildAndBind(caption, property, type);
        addComponent(field);
        return field;
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

    public ComboBox addCombo(String caption, String property, AxContainer container, String display) {
        /*ComboBox field = fieldGroup.buildAndBind(caption, property, ComboBox.class);
        field.setItemCaptionPropertyId(display);
        addComponent(field);
        return field;*/
        ComboBox combo = new ComboBox(caption, container);
        combo.setItemCaptionPropertyId(display);
        combo.setConverter(new AxConverter(container));
        combo.setTextInputAllowed(false);
        combo.setImmediate(true);
        fieldGroup.bind(combo, property);
        addComponent(combo);
        return combo;
    }

    public <T extends Enum> ComboBox addCombo(String caption, String property, Class<T> enumClass) {
        BeanItemContainer<T> container = new BeanItemContainer<T>(enumClass);
        container.addAll(EnumSet.allOf(enumClass));
        ComboBox combo = new ComboBox(caption, container);
        //combo.setItemCaptionPropertyId(display);
        combo.setImmediate(true);
        fieldGroup.bind(combo, property);
        addComponent(combo);
        return combo;
    }
    static class AxConverter<T extends IdEntity> implements Converter<Integer, T> {

        AxContainer<T> container;

        public AxConverter(AxContainer<T> container) {
            this.container = container;
        }

        @Override
        public T convertToModel(Integer value, Class<? extends T> targetType, Locale locale) throws ConversionException {
            if (value == null) return null;
            BeanItem<T> item = container.getItem(value);
            return item.getBean();
        }

        @Override
        public Integer convertToPresentation(T value, Class<? extends Integer> targetType, Locale locale) throws ConversionException {
            if (value == null) return null;
            return value.getId();
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
