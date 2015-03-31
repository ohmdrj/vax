package cz.req.ax;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
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
        AxUtils.closeWindows();
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

        Label label = new Label("Ale toto je nepříjemné ...");
        label.addStyleName("h2");
        label.addStyleName("primary");
        Button button = new Button("Pokračovat", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                navigate("Main");
            }
        });
        layoutCss().components(label,button, component);
    }
}
