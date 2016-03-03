package cz.req.ax.builders;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import cz.req.ax.Ax;

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

    public LabelBuilder header(int level) {
        return style("h" + level);
    }

    public LabelBuilder colored() {
        return style(Ax.COLORED);
    }

    public LabelBuilder tiny() {
        return style(Ax.TINY);
    }

    public LabelBuilder small() {
        return style(Ax.SMALL);
    }

    public LabelBuilder large() {
        return style(Ax.LARGE);
    }

    public LabelBuilder huge() {
        return style(Ax.HUGE);
    }

    public LabelBuilder bold() {
        return style(Ax.BOLD);
    }

    public LabelBuilder light() {
        return style(Ax.LIGHT);
    }

    public LabelBuilder alignLeft() {
        return style(Ax.ALIGN_LEFT);
    }

    public LabelBuilder alignRight() {
        return style(Ax.ALIGN_RIGHT);
    }

    public LabelBuilder success() {
        return style(Ax.SUCCESS);
    }

    public LabelBuilder failure() {
        return style(Ax.FAILURE);
    }

}
