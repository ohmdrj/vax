package cz.req.ax;

import com.google.common.base.Strings;
import com.vaadin.data.Validator;
import com.vaadin.event.ListenerMethod;
import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.Page;
import com.vaadin.server.ServerRpcManager;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 16.2.2016
 */
public class AxErrorHandler implements ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(AxErrorHandler.class);

    private String errorView;

    public void setErrorView(String errorView) {
        this.errorView = errorView;
    }

    @Override
    public void error(ErrorEvent event) {
        Throwable throwable = findRelevantThrowable(event.getThrowable());
        try {
            Validator.InvalidValueException invalidValueCause = findInvalidValueCause(throwable);
            if (invalidValueCause != null) {
                handleInvalidValue(invalidValueCause);
            } else {
                handleUnknownError(throwable);
            }
        } catch (Throwable handlerThrowable) {
            logger.error("Vaadin error: ", throwable);
            logger.error("Ax error handler failed",  handlerThrowable);
        }
    }

    protected static Throwable findRelevantThrowable(Throwable t) {
        // From DefaultErrorHandler
        try {
            if ((t instanceof ServerRpcManager.RpcInvocationException)
                    && (t.getCause() instanceof InvocationTargetException)) {
                /*
                 * RpcInvocationException (that always wraps irrelevant
                 * java.lang.reflect.InvocationTargetException) might only be
                 * relevant for core Vaadin developers.
                 */
                return findRelevantThrowable(t.getCause().getCause());
            } else if (t instanceof ListenerMethod.MethodException) {
                /*
                 * Method exception might only be relevant for core Vaadin
                 * developers.
                 */
                return t.getCause();
            }
        } catch (Exception e) {
            // NOP, just return the original one
        }
        return t;
    }

    public static Validator.InvalidValueException findInvalidValueCause(Throwable throwable) {
        while (throwable != null) {
            if (throwable instanceof Validator.InvalidValueException) {
                return (Validator.InvalidValueException) throwable;
            }
            throwable = throwable.getCause();
        }
        return null;
    }

    protected void handleInvalidValue(Validator.InvalidValueException exception) {
        String message = getInvalidValueMessage(exception);
        logger.debug("InvalidValueException: {}", message);
        Ax.notify(message).show();
    }

    protected String getInvalidValueMessage(Validator.InvalidValueException exception) {
        if (Strings.isNullOrEmpty(exception.getMessage())) {
            return exception instanceof Validator.EmptyValueException
                    ? AxUtils.DEFAULT_REQUIRED_ERROR
                    : AxUtils.DEFAULT_INVALID_VALUE_ERROR;
        }
        return exception.getMessage();
    }

    protected void handleUnknownError(Throwable throwable) {
        logger.error("Vaadin error: ", throwable);
        if (Strings.isNullOrEmpty(errorView)) {
            Ax.message("Nastala chyba při vykonávání operace.").error(throwable).show();
        } else {
            UI ui = UI.getCurrent();
            ui.getSession().setAttribute(Throwable.class, throwable);
            ui.getNavigator().navigateTo(errorView);
        }
    }

}
