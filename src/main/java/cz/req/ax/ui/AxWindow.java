package cz.req.ax.ui;

import com.google.common.base.Strings;
import com.vaadin.ui.*;
import cz.req.ax.*;
import cz.req.ax.builders.*;
import org.springframework.util.Assert;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 14.2.2016
 */
public class AxWindow extends Window {

    private CssLayout rootLayout;
    private CssLayout footerLayout;
    private Button closeButton;
    private boolean focusOnFirstField;

    public AxWindow() {
        this(null, true);
    }

    public AxWindow(String caption) {
        this(caption, true);
    }

    public AxWindow(String caption, boolean useDefaults) {
        super(caption);

        footerLayout = Ax.cssLayout().style("ax-window-footer").get();
        rootLayout = Ax.cssLayout(footerLayout).style("ax-window-root").get();

        super.setContent(rootLayout);
        addStyleName("ax-window");

        if (useDefaults) {
            new AxWindowBuilder(this, true);
        }
    }

    public boolean isFocusOnFirstField() {
        return focusOnFirstField;
    }

    public void setFocusOnFirstField(boolean focusOnFirstField) {
        this.focusOnFirstField = focusOnFirstField;
    }

    @Override
    public void setContent(Component component) {
        if (rootLayout != null) {
            rootLayout.removeAllComponents();
            if (component != null) {
                component.addStyleName("ax-window-content");
                rootLayout.addComponent(component);
            }
            rootLayout.addComponent(footerLayout);
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
            addStyleName("headerless");
        } else {
            removeStyleName("headerless");
        }
    }

    private AxWindowButtonBuilder addButton(AxWindowButtonBuilder builder, boolean asFirst) {
        AxWindowButton button = builder.window(this).get();
        if (asFirst) {
            footerLayout.addComponent(button, 0);
        } else {
            footerLayout.addComponent(button);
        }
        return builder;
    }

    public AxWindowButtonBuilder addButton() {
        return addButton(new AxWindowButtonBuilder(), false);
    }

    public AxWindowButtonBuilder addButton(String caption) {
        return addButton().caption(caption);
    }

    public AxWindowButtonBuilder addButton(AxAction<?> action) {
        return addButton(action.createWindowButton(), false);
    }

    public AxWindowButtonBuilder addButton(AxWindowButton button) {
        return addButton(new AxWindowButtonBuilder(button, false), false);
    }

    public AxWindowButtonBuilder addCloseButton() {
        return addCloseButton("Zavřít");
    }

    public AxWindowButtonBuilder addCloseButton(String caption) {
        if (closeButton != null) {
            throw new IllegalStateException("Window has already a close button!");
        }
        AxWindowButtonBuilder builder = new AxWindowButtonBuilder();
        closeButton = addButton(builder, true).caption(caption).style("close-button")
                .escKeyShortcut().closesWindow().get();
        return builder;
    }

    public void removeCloseButton() {
        if (closeButton != null) {
            footerLayout.removeComponent(closeButton);
            closeButton = null;
        }
    }

    public void show(UI ui) {
        if (ui == null) {
            ui = UI.getCurrent();
        }

        Assert.notNull(ui, "Vaadin UI not available");
        ui.addWindow(this);

        if (focusOnFirstField) {
            AxUtils.focusFirst(rootLayout);
        } else {
            focus();
        }
    }

    public void show() {
        show(null);
    }

}
