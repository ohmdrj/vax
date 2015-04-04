package cz.req.ax;

import com.vaadin.server.Resource;
import com.vaadin.ui.CssLayout;
import org.springframework.util.Assert;

import java.util.function.Consumer;

public class Switcher<T> extends CssLayout {

    T value;
    Consumer<T> action;

    public Switcher(T value, Consumer<T> action) {
        Assert.notNull(value);
        Assert.notNull(action);
        this.value = value;
        this.action = action;
        setStyleName("switcher");
    }

    public Switcher<T> button(String name, Resource icon, T value) {
        Assert.notNull(value);
        AxAction<T> action = new AxAction<T>().caption(name).icon(icon).value(value).action(this.action);
        if (value.equals(this.value)) action.primary();
        addComponent(action.button());
        return this;
    }

}
