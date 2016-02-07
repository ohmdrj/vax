package cz.req.ax.builders;

import com.vaadin.ui.OptionGroup;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class OptionGroupBuilder extends AbstractSelectBuilder<OptionGroup> {

    public OptionGroupBuilder() {
        super(new OptionGroup());
    }

    public OptionGroupBuilder(OptionGroup field) {
        super(field);
    }

}
