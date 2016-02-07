package cz.req.ax.builders;

import com.vaadin.ui.TextField;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class TextFieldBuilder extends AbstractTextFieldBuilder<TextField> {

    public TextFieldBuilder() {
        super(new TextField());
    }

    public TextFieldBuilder(TextField field) {
        super(field);
    }

}
