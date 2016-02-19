package cz.req.ax.builders;

import com.vaadin.event.LayoutEvents;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Component;

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
