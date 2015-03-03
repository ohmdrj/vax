package cz.req.ax;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.xpoft.vaadin.VaadinView;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author by Ondřej Buriánek, burianek@marbes.cz.
 * @since 16.1.15
 */

@Component
@Scope("prototype")
@VaadinView(ExceptionView.NAME)
public class ExceptionView extends AxView {

    public static final String NAME = "Exception";

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        AbstractComponent component;
        Throwable exception = getSession().getAttribute(Throwable.class);
        if (exception != null) {
            Accordion accordion = new Accordion();
            do {
                StringWriter stringWriter = new StringWriter();
                exception.printStackTrace(new PrintWriter(stringWriter));
                TextArea stacktrace = new TextArea("Stacktrace", stringWriter.toString());
                stacktrace.setWidth(100, Unit.PERCENTAGE);
                stacktrace.setHeight(200, Unit.PIXELS);
                stacktrace.setReadOnly(true);

                accordion.addTab(stacktrace, exception.getMessage(), FontAwesome.EXCLAMATION_CIRCLE);
                component = stacktrace;

                if (exception.getCause() != null && !exception.getCause().equals(exception)) {
                    exception = exception.getCause();
                } else {
                    exception = null;
                }
            } while (exception != null);

            accordion.setSelectedTab(component);
            component = accordion;

        } else {
            component = new Label("Neznámá chyba :(");
        }

        Label label = new Label("Ale toto je nepříjemné...");
        label.addStyleName("h2");
        label.addStyleName("primary");
        layoutCss().components(label, component);
    }
}
