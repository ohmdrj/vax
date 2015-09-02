package cz.req.ax;

import com.sun.istack.internal.Nullable;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;

import java.util.Locale;
import java.util.stream.Stream;

public abstract class AxView extends RootLayout implements View, Navigation, Components {

    TabSheet tabSheet;
    String parameters;

    protected AxView() {
        String name = getClass().getSimpleName();
        name = name.replaceAll("View", "-view").toLowerCase();
        setStyleName("page-root");
        addStyleName(name);
    }

    public String getParameters() {
        return parameters;
    }

    @Nullable
    public Integer getParameterInteger() {
        try {
            return new StringToIntegerConverter().convertToModel(parameters, Integer.class, Locale.getDefault());
        } catch (Exception ex) {
            return null;
        }
    }

    public Integer[] getParameterIntegers() {
        try {
            return Stream.of(getParameterStrings()).map(Integer::parseInt).toArray(size -> new Integer[size]);
        } catch (Exception ex) {
            return null;
        }
    }

    public String[] getParameterStrings() {
        return parameters.split("/");
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        parameters = event.getParameters();
    }

    //TODO Refactorize
    public AxView actions(AxAction... actions) {
        menuActions(actions);
        return this;
    }

    //TODO Refactorize
    public AxView components(Component... components) {
        mainComponents(components);
        return this;
    }

    //TODO Refactorize
    public AxView components(String layoutName, Component... components) {
        bodyLayout(layoutName).addComponents(components);
        return this;
    }

    public TabSheet tabSheet() {
        if (tabSheet == null) {
            tabSheet = new TabSheet();
            tabSheet.setWidth(100, Unit.PERCENTAGE);
            tabSheet.setHeightUndefined();
            tabSheet.addSelectedTabChangeListener(event -> Refresh.tryRefresh(tabSheet.getSelectedTab()));
            components(tabSheet);
        }
        return tabSheet;
    }

    public TabSheet.Tab addTabSheet(String caption, FontAwesome awesome, final ComponentWrapper component) {
        tabSheet().addSelectedTabChangeListener(event -> {
            if (tabSheet().getSelectedTab().equals(component.getComponent())) {
                Refresh.tryRefresh(component);
            }
        });
        return tabSheet().addTab(component.getComponent(), caption, awesome);
    }

    public TabSheet.Tab addTabSheet(String caption, FontAwesome awesome, Component component) {
        return tabSheet().addTab(component, caption, awesome);
    }

    public void removeAllComponents() {
        mainPanel().removeAllComponents();
    }

}
