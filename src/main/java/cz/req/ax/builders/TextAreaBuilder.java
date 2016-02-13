package cz.req.ax.builders;

import com.vaadin.ui.TextArea;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class TextAreaBuilder extends AbstractTextFieldBuilder<TextArea, TextAreaBuilder> {

    public TextAreaBuilder() {
        super(new TextArea());
    }

    public TextAreaBuilder(String caption) {
        super(new TextArea(caption));
    }

    public TextAreaBuilder(TextArea field) {
        super(field);
    }

    public TextAreaBuilder(TextArea field, boolean useDefaults) {
        super(field, useDefaults);
    }

    public TextAreaBuilder rows(int rows) {
        component.setRows(rows);
        return this;
    }

    public TextAreaBuilder wordwrap(boolean wordwrap) {
        component.setWordwrap(wordwrap);
        return this;
    }

    public TextAreaBuilder wordwrap() {
        return wordwrap(true);
    }

}
