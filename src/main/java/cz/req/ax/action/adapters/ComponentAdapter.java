package cz.req.ax.action.adapters;

import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.MenuBar;
import cz.req.ax.action.AxAction;
import cz.req.ax.ui.AxWindowButton;

import java.util.function.BooleanSupplier;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 19.2.2016
 */
public interface ComponentAdapter {

    static ComponentAdapter create(Object component){
        if (component instanceof AxWindowButton) {
            return new AxWindowButtonAdapter((AxWindowButton) component);
        }
        if (component instanceof Button) {
            return new ButtonAdapter((Button) component);
        }
        if (component instanceof MenuBar.MenuItem) {
            return new MenuItemAdapter((MenuBar.MenuItem) component);
        }
        if (component instanceof MenuBar) {
            return new MenuBarAdapter((MenuBar) component);
        }
        throw new IllegalArgumentException("Cannot adapt object of type " + component.getClass());
    }

    void setCaption(String caption);

    void setIcon(Resource icon);

    void setDescription(String description);

    void setKeyShortcut(int key, int... modifiers);

    void setEnabled(boolean enabled);

    void setVisible(boolean visible);

    void setExecution(BooleanSupplier callback);

    ComponentAdapter createChild();

}
