package cz.req.ax;

import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

import java.util.function.Supplier;

/**
 * @author by Ondřej Buriánek, burianek@marbes.cz.
 * @since 13.1.15
 */
public class ContainerRoot {

    boolean wrap = false;
    AbstractLayout root;
    InitMap childs = new InitMap();

    public ContainerRoot(AbstractLayout root) {
        this.root = root;
    }

    public ContainerRoot(AbstractLayout root, boolean wrap) {
        this.root = root;
        this.wrap = wrap;
    }

    //TODO Redesign, Suffix for inner
    public void addComponentWrapped(Component comp) {
        if (wrap) {
            CssLayout wrap = new CssLayout(comp);
            wrap.setStyleName(comp.getStyleName() + "-wrap");
//            wrap.setStyleName(comp.getStyleName());
//            comp.setStyleName(comp.getStyleName() + "-inner");
            root.addComponent(wrap);
        } else {
            root.addComponent(comp);
        }
    }

    public void addComponent(Component comp) {
        root.addComponent(comp);
    }

    public <T> T getVariable(String key, Supplier<T> sup) {
        return childs.get(key, sup);
    }

    public <T> T setVariable(String key, T var) {
        return childs.set(key, var);
    }
}
