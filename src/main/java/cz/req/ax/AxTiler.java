package cz.req.ax;

import com.vaadin.data.util.AbstractBeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import org.springframework.util.Assert;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class AxTiler<T> extends CssLayout {

    //    String entityName;
    String propertyId;
    AbstractBeanContainer<?, T> container;
    Consumer<T> action;
    Function<BeanItem<T>, AbstractComponent> factory;
    BiFunction<BeanItem<T>, AbstractComponent, AbstractComponent> modifier;

    public AxTiler() {
        setStyleName("tiler");
        setSizeUndefined();
        factory = (bean) -> {
            Assert.notNull(bean);
            Object caption = propertyId == null ? bean.getBean()
                    : bean.getItemProperty(propertyId).getValue();
            AbstractComponent button = new Button(String.valueOf(caption), event -> {
                if (action == null) return;
                T data = (T) event.getButton().getData();
                action.accept(data);
            });
            if (modifier != null) {
                button = modifier.apply(bean, button);
            }
            button.setData(bean.getBean());
            return button;
        };
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        components.forEach(c -> {
            c.setEnabled(enabled);
            if (c instanceof CssLayout) {
                ((CssLayout) c).iterator().forEachRemaining(x -> x.setEnabled(enabled));
            }
        });
    }

    public AxTiler<T> container(AbstractBeanContainer<?, T> container) {
        this.container = container;
        refresh();
        return this;
    }

    public AxTiler<T> action(Consumer<T> action) {
        this.action = action;
        return this;
    }

    public AxTiler<T> factory(Function<BeanItem<T>, AbstractComponent> factory) {
        this.factory = factory;
        return this;
    }

    public AxTiler<T> modifier(BiFunction<BeanItem<T>, AbstractComponent, AbstractComponent> modifier) {
        this.modifier = modifier;
        return this;
    }

    public AxTiler<T> tile(Function<T, CssLayout> tile) {
        this.factory = item -> tile.apply(item == null ? null : item.getBean());
        return this;
    }

    public AxTiler<T> style(String style) {
        addStyleName(style);
        return this;
    }

    public AxTiler<T> propertyId(String propertyId) {
        this.propertyId = propertyId;
        return this;
    }

    public AbstractBeanContainer<?, T> getContainer() {
        return container;
    }

    public void refresh() {
        removeAllComponents();
        if (container == null) return;
        if (container instanceof AxContainer) {
            ((AxContainer) container).refresh();
        }
        for (Object id : container.getItemIds()) {
            createTile(container.getItem(id));
        }
    }

    protected AbstractComponent createTile(BeanItem<T> bean) {
        AbstractComponent component = factory.apply(bean);
        if (component instanceof CssLayout && action != null) {
            CssLayout layout = (CssLayout) component;
            layout.addLayoutClickListener(event -> {
                T value = bean == null ? null : bean.getBean();
                action.accept(value);
            });
        }
        component.setEnabled(isEnabled());
        addComponent(component);
        return component;
    }

}
