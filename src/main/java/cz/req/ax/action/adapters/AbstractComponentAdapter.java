package cz.req.ax.action.adapters;

import com.vaadin.server.Resource;
import com.vaadin.ui.AbstractComponent;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 21.2.2016
 */
public abstract class AbstractComponentAdapter<T extends AbstractComponent> implements ComponentAdapter {

    protected T target;

    public AbstractComponentAdapter(T target) {
        this.target = target;
    }

    @Override
    public void setCaption(String caption) {
        target.setCaption(caption);
    }

    @Override
    public void setIcon(Resource icon) {
        target.setIcon(icon);
    }

    @Override
    public void setDescription(String description) {
        target.setDescription(description);
    }

    @Override
    public void setEnabled(boolean enabled) {
        target.setEnabled(enabled);
    }

    @Override
    public void setVisible(boolean visible) {
        target.setVisible(visible);
    }

}
