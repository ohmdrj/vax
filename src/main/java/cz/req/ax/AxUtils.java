package cz.req.ax;

import com.vaadin.data.Validator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Iterator;

public class AxUtils {

    private static final Logger logger = LoggerFactory.getLogger(AxUtils.class);
    public static ErrorHandler EMPTY_ERROR_HANDLER = event -> System.err.println(event);

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

    public static boolean focusFirst(Component component) {
        if (component == null) return false;
        if (component instanceof HasComponents) {
            Iterator<Component> iterator = ((HasComponents) component).iterator();
            while (iterator.hasNext()) {
                if (focusFirst(iterator.next())) {
                    return true;
                }
            }
        } else if (component instanceof Field) {
            ((Field) component).focus();
            return true;
        }
        return false;
    }

    public static Integer getParameterInteger(ViewChangeListener.ViewChangeEvent event) {
        try {
            return Integer.parseInt(event.getParameters());
        } catch (Exception ex) {
            return null;
        }
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

    public static void safeRun(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            exceptionHandler(e);
        }
    }

    public static void exceptionHandler(Exception exception) {
        // TODO AxUI ErrorHandler ???
        Validator.InvalidValueException invalidValueException = findInvalidValueCause(exception);
        if (invalidValueException != null) {
            // Validační chyby formuláře nezobrazujeme
            logger.debug("Uživatel zadal nevalidní hodnotu: " + invalidValueException.getMessage());
        } else {
            logger.error(exception.getMessage(), exception);
            new AxMessage("Nastala chyba při vykonávání akce.").error(exception.getCause()).show();
        }
    }

    private static Validator.InvalidValueException findInvalidValueCause(Throwable throwable) {
        while (throwable != null) {
            if (throwable instanceof Validator.InvalidValueException) {
                return (Validator.InvalidValueException) throwable;
            }
            throwable = throwable.getCause();
        }
        return null;
    }

    public static void appendCaptionSuffix(Component component, String suffix) {
        String caption = component.getCaption();
        if (caption != null && !caption.endsWith(suffix)) {
            component.setCaption(caption + suffix);
        }
    }

}
