package cz.req.ax.builders;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Resource;
import com.vaadin.ui.AbstractSelect;
import cz.req.ax.AxBinder;
import cz.req.ax.AxComboBox;

import java.util.EnumSet;
import java.util.function.Function;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class AbstractSelectBuilder<F extends AbstractSelect> extends FieldBuilder<Object, F, AbstractSelectBuilder<F>> {

    public AbstractSelectBuilder(F field) {
        super(field);
    }

    public AbstractSelectBuilder<F> container(Container container) {
        field.setContainerDataSource(container);
        return this;
    }

    public <E extends Enum<E>> AbstractSelectBuilder<F> container(Class<E> enumType) {
        return container(new BeanItemContainer<E>(enumType, EnumSet.allOf(enumType)));
    }

    public AbstractSelectBuilder<F> multiSelect(boolean multiSelect) {
        field.setMultiSelect(multiSelect);
        return this;
    }

    public AbstractSelectBuilder<F> disableNullSelection() {
        return nullSelection(false);
    }

    public AbstractSelectBuilder<F> nullSelection(boolean nullSelection) {
        field.setNullSelectionAllowed(nullSelection);
        return this;
    }

    public AbstractSelectBuilder<F> nullSelectionItem(Object itemId) {
        field.setNullSelectionItemId(itemId);
        return this;
    }

    public AbstractSelectBuilder<F> itemCaption(Object propertyId) {
        field.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        field.setItemCaptionPropertyId(propertyId);
        return this;
    }

    public <V> AbstractSelectBuilder<F> itemCaption(Function<V, String> converter) {
        if (field instanceof AxComboBox) {
            ((AxComboBox<V>) field).setItemCaptionFunction(converter);
        }
        return this;
    }

    public AbstractSelectBuilder<F> itemIcon(Object propertyId) {
        field.setItemIconPropertyId(propertyId);
        return this;
    }

    public <V> AbstractSelectBuilder<F> itemIcon(Function<V, Resource> converter) {
        if (field instanceof AxComboBox) {
            ((AxComboBox<V>) field).setItemIconFunction(converter);
        }
        return this;
    }

}
