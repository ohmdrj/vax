package cz.req.ax.action.adapters;

import com.vaadin.ui.Button;
import cz.req.ax.action.AxAction;

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
    public void updateState(AxAction<?> action) {
        button.setCaption(action.getCaption());
        button.setIcon(action.getIcon());
        button.setDescription(action.getDescription());
        if (action.getShortcutKey() >= 0) {
            button.setClickShortcut(action.getShortcutKey(), action.getShortcutModifiers());
        } else {
            button.removeClickShortcut();
        }
        button.setEnabled(action.isEnabled());
        button.setVisible(action.isVisible());
    }

    @Override
    public void setCallback(BooleanSupplier callback) {
        button.addClickListener(e -> callback.getAsBoolean());

    }

    @Override
    public ComponentAdapter createChild() {
        return null;
    }

}
