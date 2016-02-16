package cz.req.ax.builders;

import com.vaadin.ui.Button;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 15.2.2016
 */
public class ButtonBuilder extends AbstractButtonBuilder<Button, ButtonBuilder> {

    public ButtonBuilder() {
        super(new Button(), true);
    }

    public ButtonBuilder(Button target, boolean useDefaults) {
        super(target, useDefaults);
    }

}
