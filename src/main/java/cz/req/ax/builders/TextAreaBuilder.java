package cz.req.ax.builders;

import com.vaadin.ui.TextArea;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class TextAreaBuilder extends AbstractTextFieldBuilder<TextArea, TextAreaBuilder> {

    public TextAreaBuilder() {
        super(new TextArea(), true);
    }

    public TextAreaBuilder(TextArea target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public TextAreaBuilder rows(int rows) {
        target.setRows(rows);
        return this;
    }

    public TextAreaBuilder wordwrap(boolean wordwrap) {
        target.setWordwrap(wordwrap);
        return this;
    }

    public TextAreaBuilder wordwrap() {
        return wordwrap(true);
    }

}
