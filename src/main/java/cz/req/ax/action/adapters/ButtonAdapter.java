package cz.req.ax.action.adapters;

import com.vaadin.server.Resource;
import com.vaadin.ui.Button;

import java.util.function.BooleanSupplier;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 19.2.2016
 */
class ButtonAdapter extends AbstractComponentAdapter<Button> {

    public ButtonAdapter(Button target) {
        super(target);
    }

    @Override
    public void setKeyShortcut(int key, int... modifiers) {
        target.setClickShortcut(key, modifiers);
    }

    @Override
    public void setExecution(BooleanSupplier callback) {
        target.addClickListener(e -> callback.getAsBoolean());
    }

    @Override
    public ComponentAdapter createChild() {
        return null;
    }

}
