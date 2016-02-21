package cz.req.ax;

import com.vaadin.data.Container;
import com.vaadin.server.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * @author by Ondřej Buriánek, burianek@marbes.cz.
 * @since 16.4.15
 * @deprecated use {@link cz.req.ax.ui.AxComboBox} or {@link Ax#comboBox()}
 */
@Deprecated
public class AxComboBox<T> extends cz.req.ax.ui.AxComboBox<T> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public AxComboBox() {
    }

    public AxComboBox(String caption, Container dataSource, Function<T, String> itemCaptionFunction) {
        super(caption);
        setContainerDataSource(dataSource);
        setItemCaptionFunction(itemCaptionFunction);
    }

    public AxComboBox(String caption, Container dataSource, String itemCaption) {
        super(caption);
        setContainerDataSource(dataSource);
        setItemCaptionMode(ItemCaptionMode.PROPERTY);
        setItemCaptionPropertyId(itemCaption);
    }

    @Override
    public String getItemCaption(Object itemId) {
        try {
            return super.getItemCaption(itemId);
        } catch (Exception e) {
            logger.error("AxComboBox getItemCaption error", e);
            return "#";
        }
    }

    @Override
    public Resource getItemIcon(Object itemId) {
        try {
            return super.getItemIcon(itemId);
        } catch (Exception e) {
            logger.error("AxComboBox getItemIcon error", e);
            return null;
        }
    }

}
