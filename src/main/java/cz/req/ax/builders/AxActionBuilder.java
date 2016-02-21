package cz.req.ax.builders;

import com.google.common.base.Supplier;
import com.vaadin.server.Resource;
import com.vaadin.ui.MenuBar;
import cz.req.ax.Ax;
import cz.req.ax.action.AxAction;
import cz.req.ax.util.ToBooleanFunction;

import java.util.Collection;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 15.2.2016
 */
public class AxActionBuilder<T> extends AxBuilder<AxAction<T>, AxActionBuilder<T>> {

    public AxActionBuilder() {
        super(new AxAction<>(), true);
    }

    public AxActionBuilder(AxAction<T> target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public AxActionBuilder<T> caption(String caption) {
        target.setCaption(caption);
        return this;
    }

    public AxActionBuilder<T> icon(Resource icon) {
        target.setIcon(icon);
        return this;
    }

    public AxActionBuilder<T> description(String description) {
        target.setDescription(description);
        return this;
    }

    public AxActionBuilder<T> shortcut(int key, int... modifiers) {
        target.setShortcut(key, modifiers);
        return this;
    }

    public AxActionBuilder<T> enabled(boolean enabled) {
        target.setEnabled(enabled);
        return this;
    }

    public AxActionBuilder<T> enabled() {
        return enabled(true);
    }

    public AxActionBuilder<T> disabled() {
        return enabled(false);
    }

    public AxActionBuilder<T> visible(boolean visible) {
        target.setVisible(visible);
        return this;
    }

    public AxActionBuilder<T> visible() {
        return visible(true);
    }

    public AxActionBuilder<T> hidden() {
        return visible(false);
    }

    public AxActionBuilder<T> runBefore(Runnable runnable) {
        target.addRunBefore(runnable);
        return this;
    }

    public AxActionBuilder<T> runBefore(BooleanSupplier cancelableRunnable) {
        target.addRunBefore(cancelableRunnable);
        return this;
    }

    public <NT extends T> AxActionBuilder<NT> input(NT input) {
        target.setInput(input);
        return (AxActionBuilder<NT>) this;
    }

    public <NT extends T> AxActionBuilder<NT> input(Supplier<NT> inputSupplier) {
        target.setInput(inputSupplier);
        return (AxActionBuilder<NT>) this;
    }

    public AxActionBuilder<T> run(Runnable runnable) {
        target.setRun(runnable);
        return this;
    }

    public AxActionBuilder<T> run(BooleanSupplier cancelableRunnable) {
        target.setRun(cancelableRunnable);
        return this;
    }

    public AxActionBuilder<T> run(Consumer<T> runnableWithInput) {
        target.setRun(runnableWithInput);
        return this;
    }

    public AxActionBuilder<T> run(ToBooleanFunction<T> cancelableRunnableWithInput) {
        target.setRun(cancelableRunnableWithInput);
        return this;
    }

    public AxActionBuilder<T> runAfter(Runnable runnable) {
        target.addRunAfter(runnable);
        return this;
    }

    public AxActionBuilder<T> runAfter(BooleanSupplier cancelableRunnable) {
        target.addRunAfter(cancelableRunnable);
        return this;
    }

    public AxActionBuilder<T> onError(Consumer<RuntimeException> handler) {
        target.setErrorHandler(handler);
        return this;
    }

    public AxActionBuilder<?> subaction() {
        return target.addSubaction();
    }

    public AxActionBuilder<?> subaction(String caption) {
        return target.addSubaction(caption);
    }

    public AxActionBuilder<?> subactions(Collection<? extends AxAction<?>> actions) {
        target.addSubactions(actions);
        return this;
    }

    public AxActionBuilder<?> subactions(AxAction<?>... actions) {
        target.addSubactions(actions);
        return this;
    }

    public AxActionBuilder<?> parent() {
        return new AxActionBuilder<>(target.getParent(), false);
    }

    public AxActionBuilder<T> clone() {
        return new AxActionBuilder<>(target.clone(), false);
    }

    public ButtonBuilder button() {
        return target.createButton();
    }

    public MenuBarBuilder menuBar() {
        return target.createMenuBar();
    }

    public MenuBuilder menu(MenuBar menuBar) {
        return target.createMenu(menuBar);
    }

    public MenuBuilder menu(MenuBar.MenuItem parentItem) {
        return target.createMenu(parentItem);
    }

    public MenuItemBuilder menuItem(MenuBar menuBar) {
        return target.createMenuItem(menuBar);
    }

    public MenuItemBuilder menuItem(MenuBar.MenuItem parentItem) {
        return target.createMenuItem(parentItem);
    }

}
