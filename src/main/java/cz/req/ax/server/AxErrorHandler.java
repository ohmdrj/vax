package cz.req.ax.server;

import com.google.common.base.Strings;
import com.vaadin.data.Validator;
import com.vaadin.event.ListenerMethod;
import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.ServerRpcManager;
import com.vaadin.ui.UI;
import cz.req.ax.Ax;
import cz.req.ax.AxUtils;
import cz.req.ax.navigator.AxNavigator;
import cz.req.ax.ui.AxUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 16.2.2016
 */
public class AxErrorHandler implements ErrorHandler {

    public static AxErrorHandler getCurrent() {
        return AxUI.getCurrent().getErrorHandler();
    }

    private static final Logger logger = LoggerFactory.getLogger(AxErrorHandler.class);

    @Override
    public void error(ErrorEvent event) {
        handleThrowable(findRelevantThrowable(event.getThrowable()));
    }

    protected Throwable findRelevantThrowable(Throwable t) {
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

    public void handleThrowable(Throwable throwable) {
        try {
            Validator.InvalidValueException invalidValueCause = findInvalidValueCause(throwable);
            if (invalidValueCause != null) {
                handleInvalidValueException(invalidValueCause);
            } else if (isUnknownViewException(throwable)) {
                handleUnknownViewException(throwable);
            } else {
                handleGenericException(throwable);
            }
        } catch (Throwable handlerThrowable) {
            logger.error("Vaadin error: ", throwable);
            logger.error("Ax error handler failed",  handlerThrowable);
        }
    }

    protected Validator.InvalidValueException findInvalidValueCause(Throwable throwable) {
        while (throwable != null) {
            if (throwable instanceof Validator.InvalidValueException) {
                return (Validator.InvalidValueException) throwable;
            }
            throwable = throwable.getCause();
        }
        return null;
    }

    protected void handleInvalidValueException(Validator.InvalidValueException exception) {
        String message = AxUtils.getInvalidValueMessage(exception);
        logger.debug("InvalidValueException: {}", message);
        Ax.notify(message).show();
    }

    protected boolean isUnknownViewException(Throwable throwable) {
        String message = throwable.getMessage();
        return message != null && message.startsWith("Trying to navigate to an unknown state");
    }

    protected void handleUnknownViewException(Throwable throwable) {
        String message = "Stránka s daným URL neexistuje.";
        logger.debug("{}: {}", message, throwable.getMessage());
        UI.getCurrent().setContent(Ax.h1(message));
    }

    protected void handleGenericException(Throwable throwable) {
        if (!AxNavigator.getCurrent().navigateToErrorView(throwable)) {
            Ax.message("Nastala chyba při vykonávání operace.").error(throwable).show();
        }
    }

}
