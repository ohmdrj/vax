package cz.req.ax;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;


/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 29.4.2015
 */
public class AxMessage extends AxWindow {

    private static final int DEFAULT_WIDTH = 500;
    private static final int ERROR_WIDTH = 800;
    private static final Set<String> USER_EXCEPTIONS = ImmutableSet.of("UserException", "ConfigurationException");

    private final Label message;

    public AxMessage(String message) {
        this.message = newLabel(message, "h2");
        modal().style("window-info")
                .components(this.message)
                .size(DEFAULT_WIDTH, null)
                .centered()
                .buttonClose("Zavřít");
    }

    /**
     * @deprecated use {@link #error(Throwable)}
     */
    @Deprecated
    public AxMessage stackTrace(Throwable throwable) {
        return error(throwable);
    }

    public AxMessage error(Throwable throwable) {
        if (USER_EXCEPTIONS.contains(throwable.getClass().getSimpleName())) {
            message.setValue(throwable.getLocalizedMessage());
        } else {
            Button button = new Button("Zobrazit výpis chyby");
            button.addClickListener(event -> {
                button.setVisible(false);

                StringWriter writer = new StringWriter();
                throwable.printStackTrace(new PrintWriter(writer));
                Label stacktrace = newLabel(writer.toString(), "stacktrace");

                // Window.center() naprosto nefunkční, takže pokus o ruční zarovnání
                int x = window.getPositionX() - (ERROR_WIDTH - DEFAULT_WIDTH) / 2;
                int y = window.getPositionY() - 150; // +300px height v css
                components(stacktrace).position(x, y).size(ERROR_WIDTH, null);
            });

            components(button);
        }

        return this;
    }

}
