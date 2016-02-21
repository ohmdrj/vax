package cz.req.ax.action.adapters;

import com.vaadin.ui.MenuBar;

import java.util.function.BooleanSupplier;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 21.2.2016
 */
public class MenuBarAdapter extends AbstractComponentAdapter<MenuBar> {

    public MenuBarAdapter(MenuBar target) {
        super(target);
    }

    @Override
    public void setKeyShortcut(int key, int... modifiers) {
    }

    @Override
    public void setExecution(BooleanSupplier callback) {
    }

    @Override
    public ComponentAdapter createChild() {
        return new MenuItemAdapter(target.addItem("", null));
    }

}
