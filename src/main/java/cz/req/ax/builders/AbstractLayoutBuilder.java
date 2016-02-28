package cz.req.ax.builders;

import com.google.common.base.Objects;
import com.vaadin.event.LayoutEvents;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.Layout;
import cz.req.ax.ui.LayoutFiller;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 13.2.2016
 */
public class AbstractLayoutBuilder<L extends AbstractLayout, B extends AbstractLayoutBuilder<L, B>>
        extends ComponentBuilder<L, B> {

    public AbstractLayoutBuilder(L target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public B add(Component... components) {
        target.addComponents(components);
        return (B) this;
    }

    public B add(ComponentBuilder<?, ?> builder) {
        return add(builder.get());
    }

    public B fill(LayoutFiller filler) {
        filler.fillLayout(target);
        return (B) this;
    }

    public B spacing(boolean enabled) {
        if (target instanceof Layout.SpacingHandler) {
            ((Layout.SpacingHandler) target).setSpacing(enabled);
        }
        return (B) this;
    }

    public B margin(boolean enabled) {
        return margin(enabled, enabled, enabled, enabled);
    }

    public B marginTop(boolean enabled) {
        return margin(enabled, null, null, null);
    }

    public B marginRight(boolean enabled) {
        return margin(null, enabled, null, null);
    }

    public B marginBottom(boolean enabled) {
        return margin(null, null, enabled, null);
    }

    public B marginLeft(boolean enabled) {
        return margin(null, null, null, enabled);
    }

    private B margin(Boolean top, Boolean right, Boolean bottom, Boolean left) {
        if (target instanceof Layout.MarginHandler) {
            MarginInfo info = ((Layout.MarginHandler) target).getMargin();
            info.setMargins(Objects.firstNonNull(top, info.hasTop()),
                    Objects.firstNonNull(right, info.hasRight()),
                    Objects.firstNonNull(bottom, info.hasBottom()),
                    Objects.firstNonNull(left, info.hasLeft()));
            ((Layout.MarginHandler) target).setMargin(info);
        }
        return (B) this;
    }

    public B onClick(LayoutEvents.LayoutClickListener listener) {
        if (target instanceof LayoutEvents.LayoutClickNotifier) {
            ((LayoutEvents.LayoutClickNotifier) target).addLayoutClickListener(listener);
        }
        return (B) this;
    }

    public B onClick(Runnable listener) {
        return onClick(e -> {
            if (e.getButton() == MouseEventDetails.MouseButton.LEFT && !e.isDoubleClick()) {
                listener.run();
            }
        });
    }

}
