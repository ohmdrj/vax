package cz.req.ax.builders;

import com.vaadin.ui.TextArea;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class TextAreaBuilder extends AbstractTextFieldBuilder<TextArea> {

    public TextAreaBuilder() {
        super(new TextArea());
    }

    public TextAreaBuilder(TextArea field) {
        super(field);
    }

    public TextAreaBuilder rows(int rows) {
        field.setRows(rows);
        return this;
    }

}
