package cz.req.ax;

import com.vaadin.ui.Label;

public class AxConfirm extends AxWindow {

    protected AxConfirm(String message, Runnable confirm) {
        Label confirmLabel = new Label(message);
        confirmLabel.addStyleName("h2");
        modal().style("window-confirm").layoutCss();
        components(
                confirmLabel,
                new AxAction().caption("Budiž").style("primary")
                        .run(confirm).runAfter(this::close).button(),
                new AxAction().caption("Storno")
                        .runAfter(this::close).button());
    }

}
