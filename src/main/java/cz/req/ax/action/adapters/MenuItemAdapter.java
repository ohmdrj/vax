package cz.req.ax.action.adapters;

import com.google.common.base.Strings;
import com.vaadin.ui.MenuBar;
import cz.req.ax.action.AxAction;

import java.util.function.BooleanSupplier;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 19.2.2016
 */
class MenuItemAdapter implements ComponentAdapter {

    private MenuBar.MenuItem menuItem;

    public MenuItemAdapter(MenuBar.MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    @Override
    public void updateState(AxAction<?> action) {
        menuItem.setText(Strings.nullToEmpty(action.getCaption()));
        menuItem.setIcon(action.getIcon());
        menuItem.setDescription(action.getDescription());
        menuItem.setEnabled(action.isEnabled());
        menuItem.setVisible(action.isVisible());
    }

    @Override
    public void setCallback(BooleanSupplier callback) {
        menuItem.setCommand(e -> callback.getAsBoolean());
    }

    @Override
    public ComponentAdapter createChild() {
        return new MenuItemAdapter(this.menuItem.addItem("", null));
    }

}
