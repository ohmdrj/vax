package cz.req.ax;

import java.util.function.Supplier;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.SingleComponentContainer;


/**
 * Panel s přepínaným obsahem dle nastavené hodnoty.
 *
 * @param <T> typ přepínané hodnoty
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 14.4.2015
 */
public class Switcher<T> extends CssLayout implements SingleComponentContainer {

    private Switch<T, Component> vswitch = new Switch<>();
    private T value;

    public Switcher() {
        vswitch.action(this::setContent);
        setStyleName("switcher");
    }

    public Switcher<T> on(T value, Component component) {
        vswitch.on(value, component);
        return this;
    }

    public Switcher<T> on(T value, Supplier<Component> factory) {
        vswitch.on(value, factory);
        return this;
    }

    public Switcher<T> setValue(T value) {
        this.value = value;
        vswitch.set(value);
        return this;
    }

    public T getValue() {
        return value;
    }

    @Override
    public Component getContent() {
        return getComponentCount() > 0 ? getComponent(0) : null;
    }

    @Override
    public void setContent(Component content) {
        removeAllComponents();
        addComponent(content);
    }

}
