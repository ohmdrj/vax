package cz.req.ax;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;

import java.util.function.Supplier;

/**
 * @author by Ondřej Buriánek, burianek@marbes.cz.
 * @since 13.1.15
 */
@FunctionalInterface
public interface Container {

    ContainerRoot getRoot();

    //TODO Rename add
    default public Container components(Component... components) {
        AbstractLayout layout = layoutMain();
        for (Component component : components) {
            layout.addComponent(component);
        }
        return this;
    }

    default public Container actions(AxAction... actions) {
        menuBar().actions(actions);
        return this;
    }

    default public AxMenuBar menuBar() {
        return getRoot().getVariable(AxMenuBar.class, () -> {
            if (getRoot().getVariable(AbstractLayout.class, null) != null) {
                throw new IllegalStateException("Layout initialized before menuBar");
            }
            AxMenuBar menuBar = new AxMenuBar();
            menuBar.addStyleName("menu-bar");
            menuBar.setWidth(100, Sizeable.Unit.PERCENTAGE);
            getRoot().addComponent(menuBar);
            return menuBar;
        });
    }

    default public AbstractLayout layoutMain() {
        return getRoot().getVariable(AbstractLayout.class, () -> {
            layoutVertical();
            return getRoot().getVariable(AbstractLayout.class, null);
        });
    }

    default AbstractLayout layoutMain(Supplier<AbstractLayout> supplier) {
        return getRoot().getVariable(AbstractLayout.class, () -> {
            AbstractLayout layout = supplier.get();
            layout.addStyleName("main-panel");
            getRoot().addComponent(layout);
            return layout;
        });
    }

    //TODO Rename rootLayout
    default public Container layoutVertical() {
        getRoot().setVariable(AbstractLayout.class, layoutMain(() -> {
            VerticalLayout layout = new VerticalLayout();
//            layout.setSizeUndefined();
            getRoot().addComponent(layout);
            return layout;
        }));
        return this;
    }

    default public Container layoutHorizontal() {
        getRoot().setVariable(AbstractLayout.class, layoutMain(() -> {
            HorizontalLayout layout = new HorizontalLayout();
//            layout.setSizeUndefined();
            getRoot().addComponent(layout);
            return layout;
        }));
        return this;
    }

    default public Container layoutCss() {
        getRoot().setVariable(AbstractLayout.class, layoutMain(() -> {
            CssLayout layout = new CssLayout();
//            layout.setSizeUndefined();
            getRoot().addComponent(layout);
            return layout;
        }));
        return this;
    }

}
