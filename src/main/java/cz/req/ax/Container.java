package cz.req.ax;

import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

/**
 * @author by Ondřej Buriánek, burianek@marbes.cz.
 * @since 13.1.15
 */
@FunctionalInterface
public interface Container {

    String PAGE_MENU = "page-menu";
    String PAGE_FOOT = "page-footer";
    String PAGE_BODY = "page-body";
    String MAIN_PANEL = "main-panel";

    ContainerRoot getRoot();

    default Container mainComponents(Component... components) {
        mainPanel().addComponents(components);
        return this;
    }

    default Container menuActions(AxAction... actions) {
        menuBar().actions(actions);
        return this;
    }

    default AxMenuBar menuBar() {
        return getRoot().getVariable(PAGE_MENU, () -> {
            AxMenuBar menuBar = new AxMenuBar();
            menuBar.addStyleName("menu-bar");
//            menuBar.setWidth(100, Sizeable.Unit.PERCENTAGE);
            getRoot().addComponentWrapped(menuBar);
            return menuBar;
        });
    }

    default AbstractLayout pageBody() {
        return getRoot().getVariable(PAGE_BODY, () -> {
            CssLayout layout = new CssLayout();
            layout.addStyleName(PAGE_BODY);
            layout.setSizeUndefined();
            getRoot().addComponent(layout);
            return layout;
        });
    }

    default AbstractLayout pageFooter() {
        return getRoot().getVariable(PAGE_FOOT, () -> {
            CssLayout layout = new CssLayout();
            layout.addStyleName(PAGE_FOOT);
            layout.setSizeUndefined();
            getRoot().addComponentWrapped(layout);
            return layout;
        });
    }

    //TODO Redesign, Suffix for inner
    @Deprecated
    default <T extends AbstractLayout> T bodyLayout(String layoutName, Class<T> layoutClass) {
        return getRoot().getVariable(layoutName, () -> {
            try {
                T layout = layoutClass.newInstance();
                layout.setSizeUndefined();
                layout.addStyleName(layoutName);
                pageBody().addComponent(layout);

                /*CssLayout wrap = new CssLayout(layout);
                wrap.setStyleName(layoutName + "-wrap");
                pageBody().addComponent(wrap);*/
                return layout;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    default CssLayout bodyLayout(String layoutName) {
        return getRoot().getVariable(layoutName, () -> {
            try {
                CssLayout layout = new CssLayout();
                layout.setSizeUndefined();
                layout.addStyleName(layoutName);
                pageBody().addComponent(layout);
                return layout;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    default CssLayout bodyLayoutWrap(String layoutName) {
        return getRoot().getVariable(layoutName, () -> {
            try {
                CssLayout layout = new CssLayout();
                layout.setSizeUndefined();
                layout.addStyleName(layoutName);
                CssLayout wrap = new CssLayout(layout);
                wrap.setStyleName(layoutName + "-wrap");
                pageBody().addComponent(wrap);
                return layout;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    default AbstractLayout mainPanel() {
        return bodyLayoutWrap(MAIN_PANEL);
    }

}
