package cz.req.ax;

public class AxConfirm extends AxWindow {

    public AxConfirm(String message, Runnable confirm) {
        setSizeUndefined();
        modal().style("window-confirm").components(newLabel(message, "h2"));
        buttonAndClose(new AxAction().caption("Potvrdit").run(confirm).primary());
        buttonClose("Zrušit");
    }

}
