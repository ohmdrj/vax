package cz.req.ax.builders;

import com.vaadin.event.FieldEvents;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import cz.req.ax.Ax;
import cz.req.ax.AxUtils;

import java.util.function.Consumer;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class ComponentBuilder<C extends Component, B extends ComponentBuilder<C, B>> extends AxBuilder<C, B> {

    public ComponentBuilder(C target, boolean useDefaults) {
        super(target, useDefaults);
    }

    private String captionSuffix = Ax.defaults().getCaptionSuffix();

    public B caption(String caption) {
        target.setCaption(caption);
        AxUtils.appendCaptionSuffix(target, captionSuffix);
        return (B) this;
    }

    public B captionSuffix(String suffix) {
        AxUtils.removeCaptionSuffix(target, captionSuffix);
        captionSuffix = suffix;
        AxUtils.appendCaptionSuffix(target, captionSuffix);
        return (B) this;
    }

    public B captionVisible(boolean visible) {
        if (visible) {
            target.removeStyleName(Ax.NO_CAPTION);
        } else {
            target.addStyleName(Ax.NO_CAPTION);
        }
        return (B) this;
    }

    public B captionVisible() {
        return captionVisible(true);
    }

    public B captionHidden() {
        return captionVisible(false);
    }

    public B icon(Resource icon) {
        target.setIcon(icon);
        return (B) this;
    }

    public B style(String style) {
        target.addStyleName(style);
        return (B) this;
    }

    public B width(int width) {
        target.setWidth(width, Sizeable.Unit.PIXELS);
        return (B) this;
    }

    public B width(String width) {
        target.setWidth(width);
        return (B) this;
    }

    public B widthUndefined() {
        target.setWidthUndefined();
        return (B) this;
    }

    public B widthFull() {
        target.setWidth(100, Sizeable.Unit.PERCENTAGE);
        return (B) this;
    }

    public B height(int height) {
        target.setHeight(height, Sizeable.Unit.PIXELS);
        return (B) this;
    }

    public B height(String height) {
        target.setHeight(height);
        return (B) this;
    }

    public B heightUndefined() {
        target.setHeightUndefined();
        return (B) this;
    }

    public B heightFull() {
        target.setHeight(100, Sizeable.Unit.PERCENTAGE);
        return (B) this;
    }

    public B size(int width, int height) {
        return width(width).height(height);
    }

    public B size(String width, String height) {
        return width(width).height(height);
    }

    public B sizeUndefined() {
        return widthUndefined().heightUndefined();
    }

    public B sizeFull() {
        return widthFull().heightFull();
    }

    public B enabled(boolean enabled) {
        target.setEnabled(enabled);
        return (B) this;
    }

    public B enabled() {
        return enabled(true);
    }

    public B disabled() {
        return enabled(false);
    }

    public B visible(boolean visible) {
        target.setVisible(visible);
        return (B) this;
    }

    public B visible() {
        return visible(true);
    }

    public B hidden() {
        return visible(false);
    }

    public B readOnly(boolean readOnly) {
        target.setReadOnly(readOnly);
        return (B) this;
    }

    public B readOnly() {
        return readOnly(true);
    }

    public B focus() {
        if (target instanceof Component.Focusable) {
            ((Component.Focusable) target).focus();
        }
        return (B) this;
    }

    public B tabIndex(int index) {
        if (target instanceof Component.Focusable) {
            ((Component.Focusable) target).setTabIndex(index);
        }
        return (B) this;
    }

    public B immediate(boolean immediate) {
        if (target instanceof AbstractComponent) {
            ((AbstractComponent) target).setImmediate(immediate);
        }
        return (B) this;
    }

    public B immediate() {
        return immediate(true);
    }

    public B description(String description) {
        if (target instanceof AbstractComponent) {
            ((AbstractComponent) target).setDescription(description);
        }
        return (B) this;
    }

    public B onFocus(Consumer<C> listener) {
        if (target instanceof FieldEvents.FocusNotifier) {
            ((FieldEvents.FocusNotifier) target).addFocusListener(e -> listener.accept((C) e.getComponent()));
        }
        return (B) this;
    }

    public B onBlur(Consumer<C> listener) {
        if (target instanceof FieldEvents.BlurNotifier) {
            ((FieldEvents.BlurNotifier) target).addBlurListener(e -> listener.accept((C) e.getComponent()));
        }
        return (B) this;
    }

}
