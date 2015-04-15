package cz.req.ax;

import com.google.common.base.Joiner;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class AxAction<T> {

    Logger logger = LoggerFactory.getLogger(getClass());

    private T value;
    private String caption, description, style;
    private Boolean enabled = Boolean.TRUE;
    private Boolean right = Boolean.FALSE;
    private Supplier<String> confirm;
    private Resource icon;
    private Runnable run, runBefore, runAfter;
    private Consumer<T> action;
    private Consumer<ActionException> exception;
    private Supplier<T> variable;

    public static <T> AxAction<T> of(T value) {
        return new AxAction<T>().value(value);
    }

    //TODO DescribedFunctionInterface
    public AxAction<T> caption(String caption) {
        this.caption = caption;
        return this;
    }

    public AxAction<T> description(String description) {
        this.description = description;
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
        return style("danger");
    }

    public AxAction<T> primary() {
        return style("primary");
    }

    public AxAction<T> friendly() {
        return style("friendly");
    }

    public AxAction<T> empty() {
        return style("empty");
    }

    public AxAction<T> style(String style) {
        this.style = style;
        return this;
    }

    public AxAction<T> right() {
        this.right = Boolean.TRUE;
        return this;
    }

    public AxAction<T> enabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public AxAction<T> enabled() {
        return enabled(true);
    }

    public AxAction<T> disabled() {
        return enabled(false);
    }

    //TODO DescribedFunctionInterface
    public AxAction<T> icon(Resource icon) {
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

    public AxAction<T> exception(Consumer<ActionException> exception) {
        this.exception = exception;
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
            doExecute(Phase.RunBefore, runBefore);
            if (confirm == null) {
                doActionAndAfter();
            } else {
                String message = confirm.get();
                if (message == null) message = "Chcete pokračovat?";
                new AxConfirm(message, this::doActionAndAfter).show();
            }
        } catch (ActionException ae) {
            if (exception == null) {
                logger.error("Missing exception handler");
                navigate(ae);
            } else {
                exception.accept(ae);
            }
        } catch (Throwable th) {
            navigate(th);
        }
    }

    public void navigate(Throwable th) {
        AxUI ui = (AxUI) UI.getCurrent();
        ui.getSession().setAttribute(Throwable.class, th);
        ui.getNavigator().navigateTo(ui.errorView);
    }

    private void doActionAndAfter() throws ActionException {
        doExecute(Phase.Run, run);
        doExecute(Phase.Value, action);
        //TODO Action validate predicate?
        doExecute(Phase.RunAfter, runAfter);
    }

    private void doExecute(Phase phase, Object exec) throws ActionException {
        T tran = null;
        try {
            if (exec == null) return;
            if (exec instanceof Runnable) ((Runnable) exec).run();
            if (exec instanceof Consumer) {
                tran = getValue();
                phase = Phase.Action;
                ((Consumer<T>) exec).accept(tran);
            }
            if (exec instanceof AxAction) ((AxAction) exec).onAction();

        } catch (ActionException ae) {
            ae.setPhase(phase);
            throw ae;
        } catch (Exception ex) {
            throw new AxAction.ActionException(phase, ex);
        } catch (Throwable th) {
            throw th;
        }
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

    public String getDescription() {
        return description;
    }

    public Resource getIcon() {
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
        if (description != null) button.setDescription(description);
        if (style != null) button.addStyleName(style);
        if (right) button.addStyleName("right");
        button.addClickListener(event -> onAction());
        button.setEnabled(enabled);
        return button;
    }

    //TODO Checked exception
    public static class ActionException extends RuntimeException {

        //        Object value;
        Phase phase;

        public ActionException(String message, Throwable cause) {
            super(message, cause);
        }

        public ActionException(Phase phase, Throwable cause) {
            super("Chyba akce ve fazi " + phase, cause);
            this.phase = phase;
//            this.value = value;
        }

        public void setPhase(Phase phase) {
            this.phase = phase;
        }

        public Phase getPhase() {
            return phase;
        }

        /*public Object getValue() {
            return value;
        }*/
    }

    public enum Phase {
        RunBefore, Run, Value, Action, RunAfter
    }

    public AxAction<T> copy(AxAction other) {
        caption(other.getCaption());
        description(other.getDescription());
        icon(other.getIcon());
        style(other.getStyle());
        run(other.getRun());
        runBefore(other.getRunBefore());
        runAfter(other.getRunAfter());
        action(other.getAction());
        return this;
    }

}
