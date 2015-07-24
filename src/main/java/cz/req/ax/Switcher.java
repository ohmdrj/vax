package cz.req.ax;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.SingleComponentContainer;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;


/**
 * Panel s přepínaným obsahem dle nastavené hodnoty.
 *
 * @param <T> typ přepínané hodnoty
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 14.4.2015
 */
public class Switcher<T> extends CssLayout implements SingleComponentContainer, Supplier<T>, Consumer<T> {

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

    public Switcher<T> set(T value) {
        if (!Objects.equals(this.value, value)) {
            this.value = value;
            vswitch.set(value);
        }
        return this;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void accept(T value) {
        set(value);
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

    public void refresh() {
        vswitch.set(value);
    }

}
