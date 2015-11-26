package cz.req.ax;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;


/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 29.4.2015
 */
public class AxMessage extends AxWindow {

    private static final int DEFAULT_WIDTH = 500;
    private static final int STACKTRACE_WIDTH = 800;

    private final Label message;

    public AxMessage(String message) {
        this.message = newLabel(message, "h2");
        modal().style("window-info")
                .components(this.message)
                .size(DEFAULT_WIDTH, null)
                .centered()
                .buttonClose("Zavřít");
    }

    public AxMessage stackTrace(Throwable throwable) {
        if (throwable.getClass().getSimpleName().equals("UserException")) {
            message.setValue(throwable.getLocalizedMessage());
        } else {
            Button button = new Button("Zobrazit výpis chyby");
            button.addClickListener(event -> {
                button.setVisible(false);

                StringWriter writer = new StringWriter();
                throwable.printStackTrace(new PrintWriter(writer));
                Label stacktrace = newLabel(writer.toString(), "stacktrace");

                // Window.center() naprosto nefunkční, takže pokus o ruční zarovnání
                int x = window.getPositionX() - (STACKTRACE_WIDTH - DEFAULT_WIDTH) / 2;
                int y = window.getPositionY() - 150; // +300px height v css
                components(stacktrace).position(x, y).size(STACKTRACE_WIDTH, null);
            });

            components(button);
        }

        return this;
    }

}
