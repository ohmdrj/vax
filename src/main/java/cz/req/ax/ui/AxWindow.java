package cz.req.ax.ui;

import com.google.common.base.Strings;
import com.vaadin.ui.*;
import cz.req.ax.*;
import cz.req.ax.builders.*;
import cz.req.ax.action.AxAction;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 14.2.2016
 */
public class AxWindow extends Window {

    private CssLayout root;
    private CssLayout footer;
    private CssLayout footerLeft;
    private CssLayout footerRight;
    private List<Component> footerComponents = new ArrayList<>();
    private Map<Component, Align> footerAlignment = new HashMap<>();
    private Map<Component, Integer> footerOrder = new HashMap<>();
    private AxWindowButton closeButton;
    private AutoFocusMode autoFocusMode;

    public AxWindow() {
        this(null);
    }

    public AxWindow(String caption) {
        super(caption);

        footerLeft = Ax.cssLayout().style("ax-window-footer-left").get();
        footerRight = Ax.cssLayout().style("ax-window-footer-right").get();
        footer = Ax.cssLayout(footerLeft, footerRight).style("ax-window-footer").get();
        root = Ax.cssLayout(footer).style("ax-window-root").get();

        super.setContent(root);

        setAutoFocusMode(AutoFocusMode.FIRST_FIELD_OR_WINDOW);
        addStyleName("ax-window");
        addCloseButton();

        Ax.defaults(this);
    }

    public AutoFocusMode getAutoFocusMode() {
        return autoFocusMode;
    }

    public void setAutoFocusMode(AutoFocusMode autoFocusMode) {
        this.autoFocusMode = autoFocusMode;
    }

    @Override
    public void setContent(Component component) {
        if (root != null) {
            root.removeAllComponents();
            if (component != null) {
                component.addStyleName("ax-window-content");
                root.addComponent(component);
            }
            root.addComponent(footer);
        }
    }

    @Override
    public void setCaption(String caption) {
        super.setCaption(caption);
        updateHeaderVisibility();
    }

    @Override
    public void setClosable(boolean closable) {
        super.setClosable(closable);
        updateHeaderVisibility();
    }

    @Override
    public void setResizable(boolean resizable) {
        super.setResizable(resizable);
        updateHeaderVisibility();
    }

    @Override
    public void setDraggable(boolean draggable) {
        super.setDraggable(draggable);
        updateHeaderVisibility();
    }

    private void updateHeaderVisibility() {
        if (Strings.isNullOrEmpty(getCaption()) && !isClosable() && !isResizable() && !isDraggable()) {
            addStyleName(Ax.NO_HEADER);
        } else {
            removeStyleName(Ax.NO_HEADER);
        }
    }

    public void addFooterComponent(Component component) {
        footerComponents.add(component);

        if (component instanceof AxWindowButton) {
            AxWindowButton button = (AxWindowButton) component;
            button.setWindow(this);

            footerAlignment.put(button, button.getAlignment());
            footerOrder.put(button, button.getOrder());
        } else {
            footerAlignment.put(component, Align.LEFT);
            footerOrder.put(component, 0);
        }

        updateFooter();
    }

    public void removeFooterComponent(Component component) {
        footerComponents.remove(component);
        footerAlignment.remove(component);
        footerOrder.remove(component);

        if (component instanceof AxWindowButton) {
            ((AxWindowButton) component).setWindow(null);
        }

        updateFooter();
    }

    public void setFooterComponentAlignment(Component component, Align alignment) {
        if (getFooterComponentAlignment(component) != alignment) {
            footerAlignment.put(component, alignment);

            if (component instanceof AxWindowButton) {
                ((AxWindowButton) component).setAlignment(alignment);
            }

            updateFooter();
        }
    }

    public void setFooterComponentOrder(Component component, int order) {
        if (getFooterComponentOrder(component) != order) {
            footerOrder.put(component, order);

            if (component instanceof AxWindowButton) {
                ((AxWindowButton) component).setOrder(order);
            }

            updateFooter();
        }
    }

    public Align getFooterComponentAlignment(Component component) {
        return footerAlignment.get(component);
    }

    public Integer getFooterComponentOrder(Component component) {
        return footerOrder.get(component);
    }

    void updateFooter() {
        if (getUI() == null) {
            return; // Optimalizace - layout seskládáme až bude okno poprvé zobrazeno
        }

        footerLeft.removeAllComponents();
        footerRight.removeAllComponents();

        footerComponents.stream()
                .filter(c -> getFooterComponentAlignment(c) == Align.LEFT)
                .sorted((c1, c2) -> getFooterComponentOrder(c1).compareTo(getFooterComponentOrder(c2)))
                .forEach(footerLeft::addComponent);

        footerComponents.stream()
                .filter(c -> getFooterComponentAlignment(c) == Align.RIGHT)
                .sorted((c1, c2) -> getFooterComponentOrder(c1).compareTo(getFooterComponentOrder(c2)))
                .forEach(footerRight::addComponent);
    }

    private AxWindowButtonBuilder addButton(AxWindowButtonBuilder builder) {
        addFooterComponent(builder.get());
        return builder;
    }

    public AxWindowButtonBuilder addButton() {
        return addButton(Ax.windowButton());
    }

    public AxWindowButtonBuilder addButton(String caption) {
        return addButton(Ax.windowButton(caption));
    }

    public AxWindowButtonBuilder addButton(AxAction<?> action) {
        return addButton(action.createWindowButton());
    }

    public AxWindowButtonBuilder addButton(AxWindowButton button) {
        return addButton(new AxWindowButtonBuilder(button, false));
    }

    public AxWindowButtonBuilder addCloseButton() {
        return addCloseButton("Zavřít");
    }

    public AxWindowButtonBuilder addCloseButton(String caption) {
        removeCloseButton();
        AxWindowButtonBuilder builder = addButton(caption).secondary()
                .order(Integer.MAX_VALUE).escKeyShortcut().closesWindow();
        closeButton = builder.get();
        return builder;
    }

    public void removeCloseButton() {
        if (closeButton != null) {
            removeFooterComponent(closeButton);
            closeButton = null;
        }
    }

    public AxWindowButton getCloseButton() {
        return closeButton;
    }

    public void show(UI ui) {
        if (!beforeShow()) {
            return;
        }

        if (ui == null) {
            ui = UI.getCurrent();
        }

        Assert.notNull(ui, "Vaadin UI not available");
        ui.addWindow(this);
        updateFooter();

        if (autoFocusMode == AutoFocusMode.FIRST_FIELD_OR_WINDOW) {
            if (!AxUtils.focusOnFirstField(root)) {
                focus();
            }
        } else if (autoFocusMode == AutoFocusMode.WINDOW) {
            focus();
        }

        afterShow();
    }

    public void show() {
        show(null);
    }

    protected boolean beforeShow() {
        return true;
    }

    protected void afterShow() {
    }

    @Override
    public void close() {
        if (beforeClose()) {
            super.close();
            afterClose();
        }
    }

    protected boolean beforeClose() {
        return true;
    }

    protected void afterClose() {
    }

    public enum AutoFocusMode {
        FIRST_FIELD_OR_WINDOW,
        WINDOW,
        DISABLED;
    }

    public enum Align {
        LEFT, RIGHT
    }
}
