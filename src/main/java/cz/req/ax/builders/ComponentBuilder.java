package cz.req.ax.builders;

import com.vaadin.data.Validator;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.Resource;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;

import java.util.function.Consumer;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class ComponentBuilder<C extends Component, B extends ComponentBuilder<C, B>> {

    protected C component;

    public ComponentBuilder(C component) {
        this(component, true);
    }

    public ComponentBuilder(C component, boolean useDefaults) {
        this.component = component;
        if (useDefaults) {
            applyDefaults();
        }
    }

    protected void applyDefaults() {
        immediate();
    }

    public C component() {
        return component;
    }

    public B caption(String caption) {
        component.setCaption(caption);
        return (B) this;
    }

    public B icon(Resource icon) {
        component.setIcon(icon);
        return (B) this;
    }

    public B style(String style) {
        component.setStyleName(style);
        return (B) this;
    }

    public B enabled(boolean enabled) {
        component.setEnabled(enabled);
        return (B) this;
    }

    public B enabled() {
        return enabled(true);
    }

    public B disabled() {
        return enabled(false);
    }

    public B visible(boolean visible) {
        component.setVisible(visible);
        return (B) this;
    }

    public B visible() {
        return visible(true);
    }

    public B hidden() {
        return visible(false);
    }

    public B readOnly(boolean readOnly) {
        component.setReadOnly(readOnly);
        return (B) this;
    }

    public B readOnly() {
        return readOnly(true);
    }

    public B focus() {
        if (component instanceof Component.Focusable) {
            ((Component.Focusable) component).focus();
        }
        return (B) this;
    }

    public B tabIndex(int index) {
        if (component instanceof Component.Focusable) {
            ((Component.Focusable) component).setTabIndex(index);
        }
        return (B) this;
    }

    public B immediate(boolean immediate) {
        if (component instanceof AbstractComponent) {
            ((AbstractComponent) component).setImmediate(immediate);
        }
        return (B) this;
    }

    public B immediate() {
        return immediate(true);
    }

    public B description(String description) {
        if (component instanceof AbstractComponent) {
            ((AbstractComponent) component).setDescription(description);
        }
        return (B) this;
    }

    public B onFocus(Consumer<C> listener) {
        if (component instanceof FieldEvents.FocusNotifier) {
            ((FieldEvents.FocusNotifier) component).addFocusListener(e -> listener.accept((C) e.getComponent()));
        }
        return (B) this;
    }

    public B onBlur(Consumer<C> listener) {
        if (component instanceof FieldEvents.BlurNotifier) {
            ((FieldEvents.BlurNotifier) component).addBlurListener(e -> listener.accept((C) e.getComponent()));
        }
        return (B) this;
    }

}
