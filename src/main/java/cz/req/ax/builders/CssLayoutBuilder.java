package cz.req.ax.builders;

import com.vaadin.ui.CssLayout;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 13.2.2016
 */
public class CssLayoutBuilder extends AbstractLayoutBuilder<CssLayout, CssLayoutBuilder> {

    public CssLayoutBuilder() {
        super(new CssLayout(), true);
    }

    public CssLayoutBuilder(CssLayout target, boolean useDefaults) {
        super(target, useDefaults);
    }

    @Override
    protected void applyDefaults() {
        super.applyDefaults();
        sizeUndefined();
    }

}
