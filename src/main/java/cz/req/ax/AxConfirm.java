package cz.req.ax;

import com.vaadin.ui.*;

public abstract class AxConfirm extends Window implements MenuBar.Command {

    protected AxConfirm() {
        this("Chcete pokračovat?");
    }

    protected AxConfirm(String message) {
        super("Potvrzení");
        Label label = new Label(message);
        Button buttonOk = new Button("Budiž", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                close();
                onConfirm();
            }
        });
        buttonOk.addStyleName("primary");
        Button buttonCancel = new Button("Storno", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                close();
                onCancel();
            }
        });
        addStyleName("window-confirm");
        setContent(new CssLayout(label, buttonOk, buttonCancel));
        center();
    }

    public abstract void onConfirm();

    public void onCancel() {
    }

    @Override
    public void menuSelected(MenuBar.MenuItem selectedItem) {
        //TODO predat event? selected?
        UI.getCurrent().addWindow(this);
    }
}
