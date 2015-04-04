package cz.req.ax;

import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorHandler;
import com.vaadin.ui.*;

import java.util.ArrayList;

public class AxUtils {

    public static ErrorHandler EMPTY_ERROR_HANDLER = event -> {
    };

    /*public static <T> T getDataValue(Class<T> type, Property.ValueChangeEvent event) {

        }*/
    public static Integer getTabPosition(TabSheet tabSheet) {
        return tabSheet.getTabPosition(tabSheet.getTab(tabSheet.getSelectedTab()));
    }

    public static <T> T getDataValue(Button.ClickEvent event) {
        Object source = event.getSource();
        if (source instanceof AbstractComponent) {
            Object data = ((AbstractComponent) source).getData();
            return (T) data;
        }
        throw new IllegalArgumentException();
    }

    public static void closeWindows() {
        for (Window window : new ArrayList<>(UI.getCurrent().getWindows())) {
            UI.getCurrent().removeWindow(window);
        }
    }
}
