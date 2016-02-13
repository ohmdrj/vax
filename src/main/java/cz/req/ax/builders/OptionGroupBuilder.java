package cz.req.ax.builders;

import com.vaadin.ui.OptionGroup;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class OptionGroupBuilder extends AbstractSelectBuilder<OptionGroup, OptionGroupBuilder> {

    public OptionGroupBuilder() {
        super(new OptionGroup());
    }

    public OptionGroupBuilder(String caption) {
        super(new OptionGroup(caption));
    }

    public OptionGroupBuilder(OptionGroup field) {
        super(field);
    }

    public OptionGroupBuilder(OptionGroup field, boolean useDefaults) {
        super(field, useDefaults);
    }

    public OptionGroupBuilder htmlContent() {
        component.setHtmlContentAllowed(true);
        return this;
    }

}
