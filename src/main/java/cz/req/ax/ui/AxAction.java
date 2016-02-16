package cz.req.ax.ui;

import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.vaadin.server.Resource;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;
import cz.req.ax.Ax;
import cz.req.ax.builders.AbstractButtonBuilder;
import cz.req.ax.builders.AxWindowButtonBuilder;
import cz.req.ax.builders.ButtonBuilder;
import cz.req.ax.builders.MenuItemBuilder;
import cz.req.ax.util.ToBooleanFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 15.2.2016
 */
public class AxAction<T> {

    private static final Logger logger = LoggerFactory.getLogger(AxAction.class);

    private String caption;
    private Resource icon;
    private String description;
    private int shortcutKey = -1;
    private int[] shortcutModifiers;
    private boolean enabled = true;
    private boolean visible = true;

    private List<Object> createdComponents = new ArrayList<>();
    private Consumer<Throwable> errorHandler;

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
        for (Object component: createdComponents) {
            if (component instanceof Component) {
                ((Component) component).setCaption(caption);
            } else if (component instanceof MenuBar.MenuItem) {
                ((MenuBar.MenuItem) component).setText(caption);
            }
        }
    }

    public Resource getIcon() {
        return icon;
    }

    public void setIcon(Resource icon) {
        this.icon = icon;
        for (Object component: createdComponents) {
            if (component instanceof Component) {
                ((Component) component).setIcon(icon);
            } else if (component instanceof MenuBar.MenuItem) {
                ((MenuBar.MenuItem) component).setIcon(icon);
            }
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        for (Object component: createdComponents) {
            if (component instanceof AbstractComponent) {
                ((AbstractComponent) component).setDescription(description);
            } else if (component instanceof MenuBar.MenuItem) {
                ((MenuBar.MenuItem) component).setDescription(description);
            }
        }
    }

    public void setShortcut(int key, int... modifiers) {
        shortcutKey = key;
        shortcutModifiers = modifiers;
        for (Object component: createdComponents) {
            if (component instanceof Button) {
                ((Button) component).setClickShortcut(key, modifiers);
            }
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        for (Object component: createdComponents) {
            if (component instanceof Component) {
                ((Component) component).setVisible(visible);
            } else if (component instanceof MenuBar.MenuItem) {
                ((MenuBar.MenuItem) component).setVisible(visible);
            }
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        for (Object component: createdComponents) {
            if (component instanceof Component) {
                ((Component) component).setEnabled(enabled);
            } else if (component instanceof MenuBar.MenuItem) {
                ((MenuBar.MenuItem) component).setEnabled(enabled);
            }
        }
    }

    public void addInput(T input) {
        addInput(() -> input);
    }

    public void addInput(Supplier<? extends T> inputSupplier) {
        inputPhase = inputSupplier;
    }

    public void addRunBefore(Runnable runnable) {
        startPhases.add(0, runnable);
    }

    public void addRunBefore(BooleanSupplier cancelableRunnable) {
        startPhases.add(0, cancelableRunnable);
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

    public void setErrorHandler(Consumer<Throwable> setErrorHandler) {
        this.errorHandler = setErrorHandler;
    }

    public boolean execute() {
        try {
            return executeUnsafe();
        } catch (Throwable throwable) {
            if (errorHandler != null) {
                try {
                    errorHandler.accept(throwable);
                } catch (Throwable handlerThrowable) {
                    logger.error("AxAction error handler failed", handlerThrowable);
                    throw throwable;
                }
            }
            return false;
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

    public ButtonBuilder createButton() {
        return createButton(Ax.button());
    }

    public AxWindowButtonBuilder createWindowButton() {
        return createButton(Ax.windowButton());
    }

    public <B extends AbstractButtonBuilder> B createButton(B builder) {
        builder.caption(caption).icon(icon).description(description);
        if (builder instanceof AxWindowButtonBuilder) {
            ((AxWindowButtonBuilder) builder).onClick((BooleanSupplier) this::execute);
        } else {
            builder.onClick((Runnable) this::execute);
        }
        if (shortcutKey >= 0) {
            builder.clickShortcut(shortcutKey, shortcutModifiers);
        }
        createdComponents.add(builder.get());
        return builder;
    }

    public MenuItemBuilder createMenuItem(MenuBar menuBar) {
        return createMenuItem(Ax.menuItem(menuBar));
    }

    public MenuItemBuilder createMenuItem(MenuBar.MenuItem parentItem) {
        return createMenuItem(Ax.menuItem(parentItem));
    }

    private MenuItemBuilder createMenuItem(MenuItemBuilder builder) {
        return builder.caption(Strings.nullToEmpty(caption)).icon(icon)
                .description(description).command(this::execute);
    }

}
