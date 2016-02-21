package cz.req.ax.ui;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Resource;
import com.vaadin.ui.ComboBox;

import java.util.function.Function;

/**
 * @author by Ondřej Buriánek, burianek@marbes.cz.
 * @since 16.4.15
 */
public class AxComboBox<T> extends ComboBox {

    Function<T, String> itemCaptionFunction;
    Function<T, Resource> itemIconFunction;

    public AxComboBox() {
    }

    public AxComboBox(String caption) {
        super(caption);
    }

    public void setItemCaptionFunction(Function<T, String> itemCaptionFunction) {
        this.itemCaptionFunction = itemCaptionFunction;
    }

    public void setItemIconFunction(Function<T, Resource> itemIconFunction) {
        this.itemIconFunction = itemIconFunction;
    }

    @Override
    public String getItemCaption(Object itemId) {
        if (itemCaptionFunction != null) {
            return applyFunctionOnItem(itemId, itemCaptionFunction);
        } else {
            return super.getItemCaption(itemId);
        }
    }

    @Override
    public Resource getItemIcon(Object itemId) {
        if (itemIconFunction != null) {
            return applyFunctionOnItem(itemId, itemIconFunction);
        } else {
            return super.getItemIcon(itemId);
        }
    }

    private <R> R applyFunctionOnItem(Object itemId, Function<T, R> function) {
        if (itemId == null) {
            return null;
        }
        Item item = getItem(itemId);
        if (item == null) {
            return null;
        }
        if (item instanceof BeanItem) {
            T bean = ((BeanItem<T>) item).getBean();
            return function.apply(bean);
        } else {
            throw new IllegalArgumentException("Cannot apply AxComboBox function, item is not an instance of BeanItem");
        }
    }

}
