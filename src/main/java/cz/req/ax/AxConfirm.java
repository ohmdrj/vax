package cz.req.ax;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

import java.util.Objects;

public class AxConfirm extends AxWindow {

    private String message;
    private String confirmCaption = "Potvrdit";
    private String cancelCaption = "Zrušit";
    private Runnable confirm;
    private boolean htmlContent;

    public AxConfirm(String message) {
        this(message, null);
    }

    public AxConfirm(String message, Runnable confirm) {
        this.message = Objects.requireNonNull(message);
        this.confirm = confirm;
    }

    /**
     * @deprecated use {@link #AxConfirm(String)}, {@link #confirm(String, Runnable)}, {@link #cancel(String)}
     */
    @Deprecated
    public AxConfirm(String message, String confirmCaption, String cancelCaption, Runnable confirm) {
        this(message);
        confirm(confirmCaption, confirm);
        cancel(cancelCaption);
    }

    public AxConfirm confirm(Runnable confirm) {
        this.confirm = confirm;
        return this;
    }

    public AxConfirm confirm(String caption, Runnable confirm) {
        this.confirmCaption = caption;
        return confirm(confirm);
    }

    public AxConfirm cancel(String caption) {
        this.cancelCaption = caption;
        return this;
    }

    public AxConfirm htmlContent() {
        this.htmlContent = true;
        return this;
    }

    @Override
    public AxWindow show() {
        Label label = newLabel(message, "h2");
        if (htmlContent) {
            label.setContentMode(ContentMode.HTML);
        }
        modal().style("window-confirm").components(label);
        buttonClose(cancelCaption);
        buttonPrimary(new AxAction().caption(confirmCaption).run(confirm));
        setSizeUndefined();
        return super.show();
    }
}
