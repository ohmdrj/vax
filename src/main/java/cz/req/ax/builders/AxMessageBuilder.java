package cz.req.ax.builders;

import com.google.common.collect.ImmutableSet;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import cz.req.ax.Ax;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 15.2.2016
 */
public class AxMessageBuilder {

    private static final Set<String> USER_EXCEPTIONS = ImmutableSet.of("UserException", "ConfigurationException");

    private AxWindowBuilder windowBuilder;
    private LabelBuilder labelBuilder;

    public AxMessageBuilder(String message) {
        labelBuilder = Ax.label(message);
        windowBuilder = Ax.window().style("ax-message").content(labelBuilder.get());
    }

    public AxMessageBuilder error(Throwable throwable) {
        if (USER_EXCEPTIONS.contains(throwable.getClass().getSimpleName())) {
            labelBuilder.value(throwable.getLocalizedMessage());
        }
        Button showButton = windowBuilder.button("Zobrazit výpis chyby").leftAligned().get();
        showButton.addClickListener(event -> {
            showButton.setVisible(false);
            StringWriter writer = new StringWriter();
            throwable.printStackTrace(new PrintWriter(writer));
            TextArea stacktrace = Ax.textArea().value(writer.toString()).rows(20)
                    .wordwrap(false).readOnly().style("stacktrace").get();
            windowBuilder.content(stacktrace).centered();
        });
        return this;
    }

    public AxMessageBuilder html() {
        labelBuilder.html();
        return this;
    }

    public AxMessageBuilder width(int width) {
        windowBuilder.width(width);
        return this;
    }

    public AxMessageBuilder widthUndefined() {
        windowBuilder.widthUndefined();
        return this;
    }

    public void show(UI ui) {
        windowBuilder.show(ui);
    }

    public void show() {
        windowBuilder.show();
    }

}
