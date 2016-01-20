package cz.req.ax;

import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * @author by Ondřej Buriánek, burianek@marbes.cz.
 * @since 20.1.16
 */
public interface Push {

    Logger logger = LoggerFactory.getLogger(Push.class);

    UI getUI();

    /*default void push(Runnable push) {
        if (getUI() == null) {
            logger.debug("Missing UI on push at " + getClass());
            return;
        }
        try {
            getUI().access(push);
        } catch (Exception e) {
            logger.error("Exception on push", e);
        }
    }*/

    default void push(Consumer<UI> push) {
        if (getUI() == null) {
            logger.debug("Missing UI on push at " + getClass());
            return;
        }
        try {
            getUI().access(() -> push.accept(getUI()));
        } catch (Exception e) {
            logger.error("Exception on push", e);
        }
    }
}
