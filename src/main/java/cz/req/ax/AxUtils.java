package cz.req.ax;

import com.vaadin.data.Property;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;

public class AxUtils {

    /*public static <T> T getDataValue(Class<T> type, Property.ValueChangeEvent event) {

    }*/

    public static <T> T getDataValue(Button.ClickEvent event) {
        Object source = event.getSource();
        if (source instanceof AbstractComponent) {
            Object data = ((AbstractComponent) source).getData();
            return (T) data;
        }
        throw new IllegalArgumentException();
    }

}
