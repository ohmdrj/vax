package cz.req.ax;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.MenuBar;

public abstract class AxAction {

    private String caption;
    private FontAwesome icon;

    protected AxAction() {
    }

    protected AxAction(String caption) {
        this.caption = caption;
    }

    protected AxAction(String caption, FontAwesome icon) {
        this.caption = caption;
        this.icon = icon;
    }

    public abstract void doAction();

    public String getCaption() {
        return caption;
    }

    public FontAwesome getIcon() {
        return icon;
    }

    public Factory factory() {
        return new Factory();
    }


    public class Factory {

        BeforeAction callback;

        public Factory callback(BeforeAction callback) {
            this.callback = callback;
            return this;
        }

        public Button button() {
            Button button = new Button(caption, icon);
            button.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    if (callback != null) callback.beforeAction();
                    doAction();
                }
            });
            return button;
        }

        public MenuButton menuButton() {
            return new MenuButton(caption, icon) {
                @Override
                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    if (callback != null) callback.beforeAction();
                    doAction();
                }
            };
        }
    }

    public static interface BeforeAction {
        void beforeAction();
    }
}
