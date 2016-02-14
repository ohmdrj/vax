package cz.req.ax.builders;

import com.vaadin.ui.OptionGroup;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class OptionGroupBuilder extends AbstractSelectBuilder<OptionGroup, OptionGroupBuilder> {

    public OptionGroupBuilder() {
        super(new OptionGroup(), true);
    }

    public OptionGroupBuilder(OptionGroup target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public OptionGroupBuilder htmlAllowed() {
        target.setHtmlContentAllowed(true);
        return this;
    }

}
