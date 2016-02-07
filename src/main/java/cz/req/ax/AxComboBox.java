package cz.req.ax;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Resource;
import com.vaadin.ui.ComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.util.function.Function;

/**
 * @author by Ondřej Buriánek, burianek@marbes.cz.
 * @since 16.4.15
 */
public class AxComboBox<T> extends ComboBox {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    Function<T, String> itemCaptionFunction;
    Function<T, Resource> itemIconFunction;

    public AxComboBox() {
    }

    public AxComboBox(String caption, Container dataSource, Function<T, String> itemCaptionFunction) {
        super(caption, dataSource);
        this.itemCaptionFunction = itemCaptionFunction;
    }

    public AxComboBox(String caption, Container dataSource, String itemCaption) {
        super(caption, dataSource);
        //TODO Burianek Hotfix konverze to string pro autocomplete. Nema byt reseno pres item indexed property?
        this.itemCaptionFunction = item -> {
            if (item == null) return null;
            try {
                Object result = new PropertyDescriptor(itemCaption, item.getClass()).getReadMethod().invoke(item);
                return result == null ? null : result.toString();
            } catch (Exception e) {
                logger.info("Cannot read property " + itemCaption + " from object " + item.getClass().getCanonicalName(), e);
                return null;
            }
        };
    }

    public void setItemCaptionFunction(Function<T, String> itemCaptionFunction) {
        this.itemCaptionFunction = itemCaptionFunction;
    }

    public void setItemIconFunction(Function<T, Resource> itemIconFunction) {
        this.itemIconFunction = itemIconFunction;
    }

    @Override
    public String getItemCaption(Object itemId) {
        if (itemCaptionFunction == null) return super.getItemCaption(itemId);
        //TODO Burianek Null item text
        if (itemId == null) return null;
        try {
            Item item = getItem(itemId);
            if (item == null) return null;
            if (item instanceof BeanItem) {
                T bean = ((BeanItem<T>) item).getBean();
                return itemCaptionFunction.apply(bean);
            } else {
                throw new IllegalArgumentException("Invalid item " + item);
            }
        } catch (Exception ex) {
            logger.error("ComboBox itemCaptionFunction error", ex);
            return "#";
        }
    }

    @Override
    public Resource getItemIcon(Object itemId) {
        if (itemIconFunction == null) return super.getItemIcon(itemId);
        if (itemId == null) return null;
        try {
            Item item = getItem(itemId);
            if (item == null) return null;
            if (item instanceof BeanItem) {
                T bean = ((BeanItem<T>) item).getBean();
                return itemIconFunction.apply(bean);
            } else {
                throw new IllegalArgumentException("Invalid item " + item);
            }
        } catch (Exception ex) {
            logger.error("ComboBox itemIconFunction error", ex);
            return null;
        }
    }

}
