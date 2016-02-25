package cz.req.ax.ui;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;
import com.vaadin.util.ReflectTools;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 15.2.2016
 */
public class AxWindowButton extends Button {

    private AxWindow window;
    private boolean closeAfterClick;
    private Align alignment;

    public AxWindow getWindow() {
        return window;
    }

    public void setWindow(AxWindow window) {
        this.window = window;
    }

    public boolean isCloseAfterClick() {
        return closeAfterClick;
    }

    public void setCloseAfterClick(boolean close) {
        closeAfterClick = close;
    }

    public Align getAlignment() {
        return alignment;
    }

    public void setAlignment(Align newAlignment) {
        removeStyleName(getAlignmentStyle());
        alignment = newAlignment;
        addStyleName(getAlignmentStyle());
    }

    private String getAlignmentStyle() {
        return alignment != null ? "align-" + alignment.name().toLowerCase() : "";
    }

    @Override
    protected void fireClick() {
        super.fireClick();
        fireWindowClick();
    }

    @Override
    protected void fireClick(MouseEventDetails details) {
        super.fireClick(details);
        fireWindowClick();
    }

    private void fireWindowClick() {
        if (window != null) {
            fireEvent(new ClickEvent(this, window));
            if (closeAfterClick) {
                window.close();
            }
        }
    }

    public void addWindowClickListener(ClickListener listener) {
        addListener(ClickEvent.class, listener, ClickListener.BUTTON_CLICK_METHOD);
    }

    public void removeWindowClickListener(ClickListener listener) {
        removeListener(ClickEvent.class, listener, ClickListener.BUTTON_CLICK_METHOD);
    }

    public static class ClickEvent extends Component.Event {

        private Window window;

        public ClickEvent(Component source, Window window) {
            super(source);
            this.window = window;
        }

        public Window getWindow() {
            return window;
        }

    }

    public interface ClickListener extends Serializable {

        Method BUTTON_CLICK_METHOD = ReflectTools.findMethod(ClickListener.class, "windowButtonClick", ClickEvent.class);

        void windowButtonClick(ClickEvent event);

    }

    public enum Align {
        LEFT, RIGHT
    }

}
