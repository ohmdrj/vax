package cz.req.ax;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 29.4.2015
 */
public class AxMessage extends AxWindow {

    public AxMessage(String message) {
        modal().style("window-info").components(newLabel(message, "h2")).buttonClose("Zavřít");
        getWindow().center();
    }

}
