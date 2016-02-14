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

    public LabelBuilder value(String value) {
        target.setValue(value);
        return this;
    }

    public LabelBuilder html() {
        target.setContentMode(ContentMode.HTML);
        return this;
    }

    public LabelBuilder preformatted() {
        target.setContentMode(ContentMode.PREFORMATTED);
        return this;
    }

}
