package cz.req.ax;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;

import javax.servlet.http.Cookie;
import java.util.*;

public class AxUtils {

    public static String readCookie(String name) {
        for (Cookie cookie : VaadinService.getCurrentRequest().getCookies()) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static void writeCookie(String name, String value) {
        try {
            Cookie cookie = new Cookie(name, value);
            cookie.setPath(VaadinService.getCurrentRequest().getContextPath());
            cookie.setMaxAge(30 * 24 * 60 * 60);
            VaadinService.getCurrentResponse().addCookie(cookie);
        } catch (Exception e) {
            System.err.println("Cannot write cookie " + name + ": " + e.getMessage());
        }
    }

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

    public static boolean focusOnFirstField(Component component) {
        if (component instanceof Field) {
            // Field musíme testovat první, protože může zároveň implementovat HasComponents
            ((Field) component).focus();
            return true;
        } else if (component instanceof HasComponents) {
            Iterator<Component> iterator = ((HasComponents) component).iterator();
            while (iterator.hasNext()) {
                if (focusOnFirstField(iterator.next())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String makeURL(String base, Object... parameters) {
        StringBuilder sb = new StringBuilder(base);
        if (parameters != null && parameters.length > 0) {
            for (Object parameter : parameters) {
                if (parameter != null) {
                    sb.append("/").append(parameter.toString());
                }
            }
        }
        return sb.toString();
    }

    public static <T> T newInstance(Class<T> beanType) {
        try {
            return beanType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void appendCaptionSuffix(Component component, String suffix) {
        String caption = component.getCaption();
        if (caption != null && suffix != null && !caption.endsWith(suffix)) {
            component.setCaption(caption + suffix);
        }
    }

    public static void removeCaptionSuffix(Component component, String suffix) {
        String caption = component.getCaption();
        if (caption != null && suffix != null && caption.endsWith(suffix)) {
            component.setCaption(caption.substring(0, caption.length() - suffix.length()));
        }
    }

}
