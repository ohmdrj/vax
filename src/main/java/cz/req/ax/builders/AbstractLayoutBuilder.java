package cz.req.ax.builders;

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

}
