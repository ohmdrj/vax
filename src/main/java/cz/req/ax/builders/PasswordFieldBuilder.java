package cz.req.ax.builders;

import com.vaadin.ui.PasswordField;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class PasswordFieldBuilder extends AbstractTextFieldBuilder<PasswordField> {

    public PasswordFieldBuilder() {
        super(new PasswordField());
    }

    public PasswordFieldBuilder(PasswordField field) {
        super(field);
    }

}
