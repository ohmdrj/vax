package cz.req.ax.action.adapters;

import com.google.common.base.Strings;
import com.vaadin.server.Resource;
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
    public void setCaption(String caption) {
        menuItem.setText(Strings.nullToEmpty(caption));
    }

    @Override
    public void setIcon(Resource icon) {
        menuItem.setIcon(icon);
    }

    @Override
    public void setDescription(String description) {
        menuItem.setDescription(description);
    }

    @Override
    public void setKeyShortcut(int key, int... modifiers) {
    }

    @Override
    public void setEnabled(boolean enabled) {
        menuItem.setEnabled(enabled);
    }

    @Override
    public void setVisible(boolean visible) {
        menuItem.setVisible(visible);
    }

    @Override
    public void setExecution(BooleanSupplier callback) {
        menuItem.setCommand(e -> callback.getAsBoolean());
    }

    @Override
    public ComponentAdapter createChild() {
        menuItem.setCommand(null);
        return new MenuItemAdapter(this.menuItem.addItem("", null));
    }

}
