package cz.req.ax.builders;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.AbstractSelect;
import cz.req.ax.AxBinder;
import cz.req.ax.AxComboBox;

import java.util.EnumSet;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class AbstractSelectBuilder<F extends AbstractSelect, B extends AbstractSelectBuilder<F, B>>
        extends FieldBuilder<Object, F, B> {

    public AbstractSelectBuilder(F target, boolean useDefaults) {
        super(target, useDefaults);
    }

    @Override
    protected void applyDefaults() {
        super.applyDefaults();
        nullAllowed();
        newItemsProhibited();
        filteringMode(FilteringMode.CONTAINS);
    }

    public B container(Container container) {
        target.setContainerDataSource(container);
        return (B) this;
    }

    public <E extends Enum<E>> B container(Class<E> enumType) {
        return container(new BeanItemContainer<E>(enumType, EnumSet.allOf(enumType)));
    }

    public B select(Object value) {
        target.select(value);
        return (B) this;
    }

    public B multiSelect(boolean multiSelect) {
        target.setMultiSelect(multiSelect);
        return (B) this;
    }

    public B multiSelect() {
        return multiSelect(true);
    }

    public B singleSelect() {
        return multiSelect(false);
    }

    public B nullAllowed(boolean nullSelection) {
        target.setNullSelectionAllowed(nullSelection);
        return (B) this;
    }

    public B nullAllowed() {
        return nullAllowed(false);
    }

    public B nullProhibited() {
        return nullAllowed(false);
    }

    public B nullItem(Object itemId) {
        target.setNullSelectionItemId(itemId);
        return (B) this;
    }

    public B itemCaption(Object propertyId) {
        target.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        target.setItemCaptionPropertyId(propertyId);
        return (B) this;
    }

    public <V> B itemCaption(Function<V, String> converter) {
        if (target instanceof AxComboBox) {
            ((AxComboBox<V>) target).setItemCaptionFunction(converter);
        }
        return (B) this;
    }

    public B itemIcon(Object propertyId) {
        target.setItemIconPropertyId(propertyId);
        return (B) this;
    }

    public <V> B itemIcon(Function<V, Resource> converter) {
        if (target instanceof AxComboBox) {
            ((AxComboBox<V>) target).setItemIconFunction(converter);
        }
        return (B) this;
    }

    public B newItemsAllowed(boolean allowed) {
        target.setNewItemsAllowed(allowed);
        return (B) this;
    }

    public B newItemsAllowed() {
        return newItemsAllowed(true);
    }

    public B newItemsProhibited() {
        return newItemsAllowed(false);
    }

    public B newItemHandler(AbstractSelect.NewItemHandler handler) {
        target.setNewItemHandler(handler);
        return (B) this;
    }

    public B newItemHandler(BiConsumer<F, String> handler) {
        target.setNewItemHandler(caption -> handler.accept(target, caption));
        return (B) this;
    }

    public B filteringMode(FilteringMode filteringMode) {
        if (target instanceof AbstractSelect.Filtering) {
            ((AbstractSelect.Filtering) target).setFilteringMode(filteringMode);
        }
        return (B) this;
    }

}
