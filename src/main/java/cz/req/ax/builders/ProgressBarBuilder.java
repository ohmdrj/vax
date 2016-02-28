package cz.req.ax.builders;

import com.vaadin.ui.ProgressBar;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 28.2.2016
 */
public class ProgressBarBuilder extends FieldBuilder<Float, ProgressBar, ProgressBarBuilder> {

    public ProgressBarBuilder() {
        this(new ProgressBar(), true);
    }

    public ProgressBarBuilder(ProgressBar target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public ProgressBarBuilder indeterminate(boolean indeterminate) {
        target.setIndeterminate(indeterminate);
        return this;
    }

    public ProgressBarBuilder indeterminate() {
        return indeterminate(true);
    }

}
