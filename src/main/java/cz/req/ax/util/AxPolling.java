package cz.req.ax.util;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 18.2.2016
 */
public class AxPolling {

    private static final Logger logger = LoggerFactory.getLogger(AxPolling.class);
    public static final int DEFAULT_INTERVAL_MS = 500;

    public static AxPolling getCurrent() {
        UI ui = UI.getCurrent();
        VaadinSession session = ui.getSession();
        AxPolling polling = session.getAttribute(AxPolling.class);
        if (polling == null) {
            polling = new AxPolling(ui);
            session.setAttribute(AxPolling.class, polling);
        }
        return polling;
    }

    private UI ui;
    private Map<Object, Integer> requests = new HashMap<>();

    private AxPolling(UI ui) {
        this.ui = ui;
    }

    public Object request() {
        return request(DEFAULT_INTERVAL_MS);
    }

    public Object request(int intervalMillis) {
        return request(null, intervalMillis);
    }

    public Object request(Object requestId, int intervalMsec) {
        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
        }
        requests.put(requestId, intervalMsec);
        update();
        return requestId;
    }

    public void cancel(Object requestId) {
        requests.remove(requestId);
        update();
    }

    private void update() {
        int newInterval = requests.values().stream().reduce(Math::min).orElse(-1);
        if (newInterval != ui.getPollInterval()) {
            ui.setPollInterval(newInterval);
            logger.debug("UI polling interval: {} ms", newInterval);
        }
    }

}
