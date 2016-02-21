package cz.req.ax.action.adapters;

import com.vaadin.server.Resource;
import com.vaadin.ui.Button;

import java.util.function.BooleanSupplier;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 19.2.2016
 */
class ButtonAdapter implements ComponentAdapter {

    private Button button;

    public ButtonAdapter(Button button) {
        this.button = button;
    }

    @Override
    public void setCaption(String caption) {
        button.setCaption(caption);
    }

    @Override
    public void setIcon(Resource icon) {
        button.setIcon(icon);
    }

    @Override
    public void setDescription(String description) {
        button.setDescription(description);
    }

    @Override
    public void setKeyShortcut(int key, int... modifiers) {
        button.setClickShortcut(key, modifiers);
    }

    @Override
    public void setEnabled(boolean enabled) {
        button.setEnabled(enabled);
    }

    @Override
    public void setVisible(boolean visible) {
        button.setVisible(visible);
    }

    @Override
    public void setExecution(BooleanSupplier callback) {
        button.addClickListener(e -> callback.getAsBoolean());
    }

    @Override
    public ComponentAdapter createChild() {
        return null;
    }

}
