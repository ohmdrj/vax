package cz.req.ax.util;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 18.2.2016
 */
public class AxPolling {

    private static final Logger logger = LoggerFactory.getLogger(AxPolling.class);

    private Manager manager;
    private int intervalMsec;

    public AxPolling(int intervalMsec) {
        Assert.state(intervalMsec > 0);
        this.intervalMsec = intervalMsec;
        this.manager = Manager.getCurrent();
        this.manager.addRequest(this);
    }

    public int getInterval() {
        return intervalMsec;
    }

    public void setInterval(int intervalMsec) {
        this.intervalMsec = intervalMsec;
        this.manager.updateRequests();
    }

    public void cancel() {
        manager.removeRequest(this);
    }

    private static class Manager {

        public static Manager getCurrent() {
            UI ui = UI.getCurrent();
            VaadinSession session = ui.getSession();
            Manager manager = session.getAttribute(Manager.class);
            if (manager == null) {
                manager = new Manager(ui);
                session.setAttribute(Manager.class, manager);
            }
            return manager;
        }

        private UI ui;
        private Set<AxPolling> requests = new HashSet<>();

        public Manager(UI ui) {
            this.ui = ui;
        }

        public synchronized void addRequest(AxPolling request) {
            requests.add(request);
            updateRequests();
        }

        public synchronized void removeRequest(AxPolling request) {
            requests.remove(request);
            updateRequests();
        }

        public synchronized void updateRequests() {
            int newInterval = requests.stream().map(AxPolling::getInterval).reduce(Math::min).orElse(-1);
            if (newInterval != ui.getPollInterval()) {
                ui.setPollInterval(newInterval);
                logger.debug("UI polling interval: {} ms", newInterval);
            }
        }

    }

}
