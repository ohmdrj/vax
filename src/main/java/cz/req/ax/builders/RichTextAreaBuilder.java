package cz.req.ax.builders;

import com.vaadin.ui.RichTextArea;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class RichTextAreaBuilder extends FieldBuilder<String, RichTextArea, RichTextAreaBuilder> {

    public RichTextAreaBuilder() {
        super(new RichTextArea(), true);
    }

    public RichTextAreaBuilder(RichTextArea target, boolean useDefaults) {
        super(target, useDefaults);
    }

    @Override
    protected void applyDefaults() {
        super.applyDefaults();
        nullProhibited();
        nullRepresentation("");
    }

    public RichTextAreaBuilder nullAllowed(boolean allowed) {
        target.setNullSettingAllowed(allowed);
        return this;
    }

    public RichTextAreaBuilder nullAllowed() {
        return nullAllowed(true);
    }

    public RichTextAreaBuilder nullProhibited() {
        return nullAllowed(false);
    }

    public RichTextAreaBuilder nullRepresentation(String representation) {
        target.setNullRepresentation(representation);
        return this;
    }

}
