package cz.req.ax.builders;

import com.vaadin.ui.FormLayout;
import cz.req.ax.Ax;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 26.2.2016
 */
public class FormLayoutBuilder extends AbstractLayoutBuilder<FormLayout, FormLayoutBuilder> {

    public FormLayoutBuilder() {
        super(new FormLayout(), true);
    }

    public FormLayoutBuilder(FormLayout target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public FormLayoutBuilder light() {
        return style(Ax.LIGHT);
    }

}
