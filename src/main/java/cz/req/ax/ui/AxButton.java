package cz.req.ax.ui;

import com.vaadin.server.Resource;
import com.vaadin.ui.Button;

/**
 * @author by Ondřej Buriánek, burianek@marbes.cz.
 * @since 30.3.16
 */
public class AxButton extends Button {

    public AxButton() {
        postConstruct();
    }

    public AxButton(String caption) {
        super(caption);
        postConstruct();
    }

    public AxButton(String caption, Resource icon) {
        super(caption, icon);
        postConstruct();
    }

    public AxButton(String caption, ClickListener listener) {
        super(caption, listener);
        postConstruct();
    }

    protected void postConstruct() {
        setDisableOnClick(true);
    }

    @Override
    public void addClickListener(ClickListener listener) {
        super.addClickListener(event -> {
            try {
                listener.buttonClick(event);
            } finally {
                if (isDisableOnClick()) setEnabled(true);
            }
        });
    }

}
