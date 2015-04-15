package cz.req.ax;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vaadin.ui.Button;


/**
 * Skupina tlačítek z nichž právě jedno je vždy nastaveno jako primární.
 *
 * @param <T> typ hodnoty, dle které se přepíná primární tlačítko
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 14.4.2015
 */
public class ButtonGroup<T> implements Iterable<Button> {

    private List<Button> buttons = new ArrayList<>();
    private Switch<T, Button> vswitch = new Switch<>();

    public ButtonGroup() {
        vswitch.action(this::setPrimaryButton);
    }

    public ButtonGroup<T> button(AxAction<T> action) {
        if (action.getAction() != null) {
            action.action(action.getAction().andThen(this::setValue));
        } else {
            action.action(this::setValue);
        }
        Button button = action.button();
        vswitch.on(action.getValue(), button);
        buttons.add(button);
        return this;
    }

    public ButtonGroup<T> setValue(T value) {
        vswitch.set(value);
        return this;
    }

    private void setPrimaryButton(Button primaryButton) {
        for (Button button: buttons) {
            button.removeStyleName("primary");
        }
        primaryButton.addStyleName("primary");
    }

    @Override
    public Iterator<Button> iterator() {
        return buttons.iterator();
    }

}
