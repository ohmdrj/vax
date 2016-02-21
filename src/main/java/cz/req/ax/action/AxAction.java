package cz.req.ax.action;

import com.google.common.base.Supplier;
import com.vaadin.server.Resource;
import com.vaadin.ui.MenuBar;
import cz.req.ax.Ax;
import cz.req.ax.action.adapters.*;
import cz.req.ax.builders.*;
import cz.req.ax.util.ToBooleanFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 15.2.2016
 */
public class AxAction<T> implements Cloneable {

    private static final Logger logger = LoggerFactory.getLogger(AxAction.class);

    private String caption;
    private Resource icon;
    private String description;
    private int shortcutKey = -1;
    private int[] shortcutModifiers;
    private boolean enabled = true;
    private boolean visible = true;

    private AxAction<?> parent;
    private List<AxAction<?>> children = new ArrayList<>();
    private List<ComponentAdapter> adapters = new ArrayList<>();
    private Consumer<RuntimeException> errorHandler;

    private List<Object> startPhases = new ArrayList<>();
    private Supplier<? extends T> inputPhase;
    private Object mainPhase;
    private List<Object> endPhases = new ArrayList<>();

    public AxAction() {
    }

    public AxAction(String caption) {
        setCaption(caption);
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
        adapters.forEach(a -> a.setCaption(caption));
    }

    public Resource getIcon() {
        return icon;
    }

    public void setIcon(Resource icon) {
        this.icon = icon;
        adapters.forEach(a -> a.setIcon(icon));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        adapters.forEach(a -> a.setDescription(description));
    }

    public int[] getShortcutModifiers() {
        return shortcutModifiers;
    }

    public int getShortcutKey() {
        return shortcutKey;
    }

    public void setShortcut(int key, int... modifiers) {
        shortcutKey = key;
        shortcutModifiers = modifiers;
        if (key > 0) {
            adapters.forEach(a -> a.setKeyShortcut(key, modifiers));
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        adapters.forEach(a -> a.setVisible(visible));
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        adapters.forEach(a -> a.setEnabled(enabled));
    }

    public void addRunBefore(Runnable runnable) {
        startPhases.add(0, runnable);
    }

    public void addRunBefore(BooleanSupplier cancelableRunnable) {
        startPhases.add(0, cancelableRunnable);
    }

    public void setInput(T input) {
        setInput(() -> input);
    }

    public void setInput(Supplier<? extends T> inputSupplier) {
        inputPhase = inputSupplier;
    }

    public void setRun(Runnable runnable) {
        mainPhase = runnable;
    }

    public void setRun(Consumer<T> runnableWithInput) {
        mainPhase = runnableWithInput;
    }

    public void setRun(BooleanSupplier cancelableRunnable) {
        mainPhase = cancelableRunnable;
    }

    public void setRun(ToBooleanFunction<T> cancelableRunnableWithInput) {
        mainPhase = cancelableRunnableWithInput;
    }

    public void addRunAfter(Runnable runnable) {
        endPhases.add(runnable);
    }

    public void addRunAfter(BooleanSupplier cancelableRunnable) {
        endPhases.add(cancelableRunnable);
    }

    public void setErrorHandler(Consumer<RuntimeException> setErrorHandler) {
        this.errorHandler = setErrorHandler;
    }

    public boolean execute() {
        try {
            return executeUnsafe();
        } catch (RuntimeException throwable) {
            if (errorHandler != null) {
                try {
                    errorHandler.accept(throwable);
                    return false;
                } catch (Throwable handlerThrowable) {
                    logger.error("AxAction error handler failed", handlerThrowable);
                    throw throwable;
                }
            } else {
                throw throwable;
            }
        }
    }

    public boolean executeUnsafe() {
        for (Object phase: startPhases) {
            if (Boolean.FALSE.equals(executePhase(phase, null))) {
                return false;
            }
        }
        T input = null;
        if (inputPhase != null) {
            input = (T) executePhase(inputPhase, null);
        }
        if (mainPhase != null) {
            if (Boolean.FALSE.equals(executePhase(mainPhase, input))) {
                return false;
            }
        }
        for (Object phase: endPhases) {
            if (Boolean.FALSE.equals(executePhase(phase, null))) {
                return false;
            }
        }
        return true;
    }

    private Object executePhase(Object phase, T input) {
        if (phase instanceof Runnable) {
            ((Runnable) phase).run();
        } else if (phase instanceof BooleanSupplier) {
            return ((BooleanSupplier) phase).getAsBoolean();
        } else if (phase instanceof Consumer) {
            ((Consumer<T>) phase).accept(input);
        } else if (phase instanceof ToBooleanFunction) {
            return ((ToBooleanFunction<T>) phase).applyAsBoolean(input);
        }
        return true;
    }

    public AxAction<?> getParent() {
        return parent;
    }

    public AxActionBuilder<?> addSubaction() {
        AxActionBuilder<Object> builder = Ax.action();
        addSubaction(builder.get());
        return builder;
    }

    public AxActionBuilder<?> addSubaction(String caption) {
        return addSubaction().caption(caption);
    }

    public void addSubaction(AxAction<?> subaction) {
        Assert.state(subaction.parent == null, "Cannot add subaction that already has a parent");

        subaction.parent = this;
        children.add(subaction);

        for (ComponentAdapter adapter: adapters) {
            ComponentAdapter childAdapter = adapter.createChild();
            if (childAdapter != null) {
                subaction.addAdapter(childAdapter);
            }
        }
    }

    public void addSubactions(Collection<? extends AxAction<?>> actions) {
        actions.forEach(this::addSubaction);
    }

    public void addSubactions(AxAction<?>... actions) {
        addSubactions(Arrays.asList(actions));
    }

    public ButtonBuilder createButton() {
        return addAdapter(Ax.button());
    }

    public AxWindowButtonBuilder createWindowButton() {
        return addAdapter(Ax.windowButton());
    }

    public MenuBarBuilder createMenuBar() {
        MenuBarBuilder builder = Ax.menuBar();
        createMenu(builder.get());
        return builder;
    }

    public MenuBuilder createMenu(MenuBar menuBar) {
        return addAdapter(Ax.menu(menuBar));
    }

    public MenuBuilder createMenu(MenuBar.MenuItem parentItem) {
        return addAdapter(Ax.menu(parentItem));
    }

    public MenuItemBuilder createMenuItem(MenuBar menuBar) {
        return addAdapter(Ax.menuItem(menuBar));
    }

    public MenuItemBuilder createMenuItem(MenuBar.MenuItem parentItem) {
        return addAdapter(Ax.menuItem(parentItem));
    }

    private <T extends AxBuilder> T addAdapter(T builder) {
        addAdapter(ComponentAdapter.create(builder.get()));
        return builder;
    }

    private void addAdapter(ComponentAdapter adapter) {
        adapter.setCaption(caption);
        adapter.setIcon(icon);
        adapter.setDescription(description);
        if (shortcutKey > 0) {
            adapter.setKeyShortcut(shortcutKey, shortcutModifiers);
        }
        adapter.setEnabled(enabled);
        adapter.setVisible(visible);
        adapter.setExecution(this::execute);
        adapters.add(adapter);

        for (AxAction<?> subaction: children) {
            ComponentAdapter childAdapter = adapter.createChild();
            if (childAdapter != null) {
                subaction.addAdapter(childAdapter);
            }
        }
    }

    @Override
    public AxAction<T> clone() {
        AxAction<T> copy = new AxAction<>();
        copy.caption = caption;
        copy.icon = icon;
        copy.description = description;
        copy.shortcutKey = shortcutKey;
        copy.shortcutModifiers = shortcutModifiers;
        copy.adapters = new ArrayList<>();
        copy.errorHandler = errorHandler;
        copy.startPhases = new ArrayList<>(startPhases);
        copy.inputPhase = inputPhase;
        copy.mainPhase = mainPhase;
        copy.endPhases = new ArrayList<>(endPhases);
        return copy;
    }

}
