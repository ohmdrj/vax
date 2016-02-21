package cz.req.ax.builders;

import com.vaadin.data.util.AbstractBeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.Table;
import cz.req.ax.ui.SelectionColumn;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 21.2.2016
 */
public class TableBuilder<ID, BEAN> extends AbstractSelectBuilder<Table, TableBuilder<ID, BEAN>> {

    private static Table createTable() {
        // TODO refactor css hack
        Table table = new Table() {
            @Override
            public void setPageLength(int pageLength) {
                super.setPageLength(pageLength);
                if (pageLength > 0) {
                    addStyleName("hack-allow-v-scroll");
                } else {
                    removeStyleName("hack-allow-v-scroll");
                }
            }
        };
        table.addStyleName("hack-noscroll");
        return table;
    }

    private List<Object> columnIds = new ArrayList<>();
    
    public TableBuilder() {
        this(createTable(), true);
    }

    public TableBuilder(Table target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public TableBuilder<ID, BEAN> container(AbstractBeanContainer<ID, BEAN> container) {
        target.setContainerDataSource(container);
        return this;
    }

    public TableBuilder<ID, BEAN> headerMode(Table.RowHeaderMode mode) {
        target.setRowHeaderMode(mode);
        return this;
    }

    public TableBuilder<ID, BEAN> headerHidden() {
        return headerMode(Table.RowHeaderMode.HIDDEN);
    }

    public TableBuilder<ID, BEAN> headerIconOnly() {
        return headerMode(Table.RowHeaderMode.ICON_ONLY);
    }
    
    public TableBuilder<ID, BEAN> footerVisible(boolean visible) {
        target.setFooterVisible(visible);
        return this;
    }

    public TableBuilder<ID, BEAN> footerVisible() {
        return footerVisible(true);
    }

    public TableBuilder<ID, BEAN> footerHidden() {
        return footerVisible(false);
    }

    public TableBuilder<ID, BEAN> editable(boolean editable) {
        target.setEditable(editable);
        return this;
    }

    public TableBuilder<ID, BEAN> editable() {
        return editable(true);
    }

    public TableBuilder<ID, BEAN> uneditable() {
        return editable(false);
    }

    public TableBuilder<ID, BEAN> selectable(boolean selectable) {
        target.setEditable(selectable);
        return this;
    }

    public TableBuilder<ID, BEAN> selectable() {
        return selectable(true);
    }

    public TableBuilder<ID, BEAN> unselectable() {
        return selectable(false);
    }

    public TableBuilder<ID, BEAN> sortable(boolean sortable) {
        target.setSortEnabled(sortable);
        return this;
    }

    public TableBuilder<ID, BEAN> sortable() {
        return sortable(true);
    }

    public TableBuilder<ID, BEAN> unsortable() {
        return sortable(false);
    }
    
    public TableBuilder<ID, BEAN> cellStyle(Table.CellStyleGenerator generator) {
        target.setCellStyleGenerator(generator);
        return this;
    }

    public TableBuilder<ID, BEAN> itemDescription(Table.ItemDescriptionGenerator generator) {
        target.setItemDescriptionGenerator(generator);
        return this;
    }

    public TableBuilder<ID, BEAN> collapsingAllowed(boolean allowed) {
        target.setColumnCollapsingAllowed(allowed);
        return this;
    }

    public TableBuilder<ID, BEAN> collapsingAllowed() {
        return collapsingAllowed(true);
    }

    public TableBuilder<ID, BEAN> collapsingProhibited() {
        return collapsingAllowed(false);
    }

    public TableBuilder<ID, BEAN> reorderingAllowed(boolean allowed) {
        target.setColumnCollapsingAllowed(allowed);
        return this;
    }

    public TableBuilder<ID, BEAN> reorderingAllowed() {
        return reorderingAllowed(true);
    }

    public TableBuilder<ID, BEAN> reorderingProhibited() {
        return reorderingAllowed(false);
    }

    public TableBuilder<ID, BEAN> pageLength(int pageLength) {
        target.setPageLength(pageLength);
        return this;
    }

    public TableBuilder<ID, BEAN> disablePaging() {
        return pageLength(0);
    }

    public TableBuilder<ID, BEAN> currentPageFirstItemId(ID itemid) {
        target.setCurrentPageFirstItemId(itemid);
        return this;
    }

    public TableBuilder<ID, BEAN> currentPageFirstItemIndex(int index) {
        target.setCurrentPageFirstItemIndex(index);
        return this;
    }
    
    public TableBuilder<ID, BEAN> dragMode(Table.TableDragMode dragMode) {
        target.setDragMode(dragMode);
        return this;
    }

    public TableBuilder<ID, BEAN> dropHandler(DropHandler dropHandler) {
        target.setDropHandler(dropHandler);
        return this;
    }

    public TableBuilder<ID, BEAN> onClick(BiConsumer<ID, BEAN> listener) {
        target.addItemClickListener(e -> {
            if (e.getButton() == MouseEventDetails.MouseButton.LEFT && e.isDoubleClick()) {
                listener.accept((ID) e.getItemId(), ((BeanItem<BEAN>) e.getItem()).getBean());
            }
        });
        target.addStyleName("has-item-click-listener");
        return this;
    }

    public TableBuilder<ID, BEAN> onClick(Consumer<ID> listener) {
        return onClick((id, bean) -> listener.accept(id));
    }

    public TableColumnBuilder<ID, BEAN> column(Object propertyId) {
        columnIds.add(propertyId);
        return new TableColumnBuilder<>(this, propertyId);
    }

    public TableColumnBuilder<ID, BEAN> column(SelectionColumn<ID> selectionColumn) {
        return column(selectionColumn.getColumnId()).generator(selectionColumn);
    }

    @Override
    public Table get() {
        target.setVisibleColumns(columnIds.toArray());
        return super.get();
    }

}
