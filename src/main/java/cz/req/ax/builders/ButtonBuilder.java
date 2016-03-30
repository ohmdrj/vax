package cz.req.ax.builders;

import com.vaadin.ui.Button;
import cz.req.ax.ui.AxButton;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 15.2.2016
 */
public class ButtonBuilder extends AbstractButtonBuilder<Button, ButtonBuilder> {

    public ButtonBuilder() {
        super(new AxButton(), true);
    }

    public ButtonBuilder(Button target, boolean useDefaults) {
        super(target, useDefaults);
    }

}
