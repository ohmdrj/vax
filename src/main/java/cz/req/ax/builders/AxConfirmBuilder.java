package cz.req.ax.builders;

import com.vaadin.ui.UI;
import cz.req.ax.Ax;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 21.2.2016
 */
public class AxConfirmBuilder {

    private String confirmCaption = "Potvrdit";
    private String cancelCaption = "Zrušit";
    private Runnable callback;

    private AxWindowBuilder windowBuilder;
    private LabelBuilder labelBuilder;

    public AxConfirmBuilder(String message) {
        labelBuilder = Ax.label(message);
        windowBuilder = Ax.window().style("ax-message").content(labelBuilder.get());
    }

    public AxConfirmBuilder onConfirm(Runnable callback) {
        this.callback = callback;
        return this;
    }

    public AxConfirmBuilder confirmCaption(String caption) {
        this.confirmCaption = caption;
        return this;
    }

    public AxConfirmBuilder cancelCaption(String caption) {
        this.cancelCaption = caption;
        return this;
    }

    public AxConfirmBuilder html() {
        labelBuilder.html();
        return this;
    }

    private AxWindowBuilder finish() {
        return windowBuilder
                .closeButton(cancelCaption)
                .button(confirmCaption).primary().onClick(callback).enterKeyShortcut().closesWindow()
                .window();
    }

    public void show(UI ui) {
        finish().show(ui);
    }

    public void show() {
        finish().show();
    }

}
