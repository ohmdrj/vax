package cz.req.ax.builders;

import com.vaadin.server.Resource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 13.2.2016
 */
public class LabelBuilder extends ComponentBuilder<Label, LabelBuilder> {

    public LabelBuilder() {
        super(new Label(), true);
    }

    public LabelBuilder(Label target, boolean useDefaults) {
        super(target, useDefaults);
    }

    @Override
    protected void applyDefaults() {
        super.applyDefaults();
        sizeUndefined();
    }

    public LabelBuilder htmlContent() {
        target.setContentMode(ContentMode.HTML);
        return this;
    }

}
