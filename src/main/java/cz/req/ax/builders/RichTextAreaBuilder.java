package cz.req.ax.builders;

import com.vaadin.ui.RichTextArea;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class RichTextAreaBuilder extends FieldBuilder<String, RichTextArea, RichTextAreaBuilder> {

    public RichTextAreaBuilder() {
        super(new RichTextArea());
    }

    public RichTextAreaBuilder(RichTextArea field) {
        super(field);
    }

}
