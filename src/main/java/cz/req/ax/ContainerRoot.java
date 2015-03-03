package cz.req.ax;

import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Component;

import java.util.function.Supplier;

/**
 * @author by Ondřej Buriánek, burianek@marbes.cz.
 * @since 13.1.15
 */
public class ContainerRoot {

    AbstractLayout root;
    VarMap childs;

    public ContainerRoot(AbstractLayout root) {
        this.root = root;
        childs = new VarMap();
    }

    public void addComponent(Component c) {
        root.addComponent(c);
    }

    public <T> T getVariable(Class<T> key, Supplier<T> sup) {
        return childs.get(key, sup);
    }

    public <T> T setVariable(Class<T> key, T var) {
        return childs.set(key, var);
    }
}
