package cz.req.ax;

import com.google.common.base.Joiner;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class AxAction<T> {

    Logger logger = LoggerFactory.getLogger(getClass());

    private T value;
    private String caption, style;
    private Boolean right = Boolean.FALSE;
    private Supplier<String> confirm;
    private FontAwesome icon;
    private Runnable run, runBefore, runAfter;
    private Consumer<T> action; //TODO Action Before/After?
    private Supplier<T> variable;

    //TODO DescribedFunctionInterface
    public AxAction<T> caption(String caption) {
        this.caption = caption;
        return this;
    }

    public AxAction<T> confirm(Supplier<String> confirm) {
        this.confirm = confirm;
        return this;
    }

    public AxAction<T> confirm(String message) {
        return confirm(() -> message);
    }

    public AxAction<T> danger() {
        this.style = "danger";
        return this;
    }

    public AxAction<T> primary() {
        this.style = "primary";
        return this;
    }

    public AxAction<T> friendly() {
        this.style = "friendly";
        return this;
    }

    public AxAction<T> style(String style) {
        this.style = style;
        return this;
    }

    public AxAction<T> right() {
        this.right = Boolean.TRUE;
        return this;
    }

    //TODO DescribedFunctionInterface
    public AxAction<T> icon(FontAwesome icon) {
        this.icon = icon;
        return this;
    }

    public AxAction<T> value(Supplier<T> call) {
        variable = call;
        return this;
    }

    public AxAction<T> value(T value) {
        this.value = value;
        return this;
    }

    public AxAction<T> action(Consumer<T> action) {
        this.action = action;
        return this;
    }

    public AxAction<T> run(Runnable run) {
        this.run = run;
        return this;
    }

    public AxAction<T> runBefore(Runnable run) {
        runBefore = run;
        return this;
    }

    public AxAction<T> runAfter(Runnable run) {
        runAfter = run;
        return this;
    }

    protected void onAction() {
        try {
            doExecute(runBefore);
            if (confirm == null) {
                doActionAndAfter();
            } else {
                String message = confirm.get();
                if (message == null) message = "Chcete pokračovate?";
                new AxConfirm(message, this::doActionAndAfter).show();
            }
        } catch (Throwable th) {
            logger.error("Action error caption:" + caption + " action:" + action, th);
            UI.getCurrent().getSession().setAttribute(Throwable.class, th);
            UI.getCurrent().getNavigator().navigateTo(ExceptionView.NAME);
        }
    }

    private void doActionAndAfter() {
        doExecute(run);
        doExecute(action);
        //TODO Action validate predicate?
        doExecute(runAfter);
    }

    private void doExecute(Object o) {
        if (o == null) return;
        if (o instanceof Runnable) ((Runnable) o).run();
        if (o instanceof Consumer) ((Consumer<T>) o).accept(getValue());
        if (o instanceof AxAction) ((AxAction) o).onAction();
    }

    public T getValue() {
        return (variable == null) ? value : variable.get();
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getCaption() {
        return caption;
    }

    public FontAwesome getIcon() {
        return icon;
    }

    public String getStyle() {
        return Joiner.on("-").skipNulls().join(style, right ? "right" : null);
    }

    public Runnable getRun() {
        return run;
    }

    public Runnable getRunBefore() {
        return runBefore;
    }

    public Runnable getRunAfter() {
        return runAfter;
    }

    public Consumer<T> getAction() {
        return action;
    }

    public Button button() {
        //TODO New factory AxButton
        //TODO Caption/Icon by factory method
        //TODO Review lazy??
        Button button = new Button(caption, icon);
        if (style != null) button.addStyleName(style);
        if (right) button.addStyleName("right");
        button.addClickListener(event -> onAction());
        return button;
    }
}
