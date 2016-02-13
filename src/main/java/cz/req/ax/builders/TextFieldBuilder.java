package cz.req.ax.builders;

import com.vaadin.ui.TextField;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class TextFieldBuilder extends AbstractTextFieldBuilder<TextField, TextFieldBuilder> {

    public TextFieldBuilder() {
        super(new TextField());
    }

    public TextFieldBuilder(TextField field) {
        super(field);
    }

    public TextFieldBuilder(String caption) {
        super(new TextField(caption));
    }

    public TextFieldBuilder(TextField field, boolean useDefaults) {
        super(field, useDefaults);
    }

}
