package cz.req.ax.ui;

import com.google.common.collect.ImmutableList;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.Table;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 18.1.2016
 */
public class SelectionColumn<ID> implements Table.ColumnGenerator {

    public static final String ID = "_selection";
    private static final String RADIO_STYLE = "radio";

    private Table table;
    private String columnId;
    private Table.HeaderClickListener headerClickListener = e -> {
        if (e.getPropertyId().equals(columnId) && e.getButton() == MouseEventDetails.MouseButton.LEFT && !e.isDoubleClick()) {
            toggleItemSelection();
        }
    };
    private ItemClickEvent.ItemClickListener itemClickListener = e -> toggleItemSelected((ID) e.getItemId());
    private boolean multiselect;
    private boolean nullSelectionAllowed;
    private boolean headerCheckbox = true;
    private boolean rowClickSelection;
    private Map<ID, CheckBox> checkBoxMap = new HashMap<>();
    private Set<SelectionChangeListener<ID>> listeners = new LinkedHashSet<>();
    private Set<ID> preselectedItemIds = new HashSet<>();

    public SelectionColumn() {
        this(ID);
    }

    public SelectionColumn(String columnId) {
        this.columnId = Objects.requireNonNull(columnId);
    }

    public String getColumnId() {
        return columnId;
    }

    @Override
    public Object generateCell(Table source, Object itemId, Object columnId) {
        if (table == null) {
            table = source;
            table.addItemSetChangeListener(e -> destroyCheckBoxes());
            updateHeaderCheckBox();
            updateRowClickSelection();
        } else if (table != source) {
            throw new IllegalStateException("Selection column cannot be used for multiple tables.");
        }

        CheckBox checkBox = new CheckBox();
        checkBox.setImmediate(true);
        checkBox.addValueChangeListener(e -> {
            Object value = e.getProperty().getValue();
            if (!multiselect && Boolean.TRUE.equals(value)) {
                for (CheckBox otherCheckBox : checkBoxMap.values()) {
                    if (otherCheckBox != checkBox) {
                        otherCheckBox.setInternalValue(false);
                    }
                }
            }
            if (!nullSelectionAllowed && Boolean.FALSE.equals(value)) {
                if (!isAnyItemSelected()) {
                    checkBox.setInternalValue(true);
                }
            }
            fireItemSelectionChange();
        });
        if (!multiselect && !nullSelectionAllowed) {
            checkBox.addStyleName(RADIO_STYLE);
        }
        if (preselectedItemIds.remove(itemId) || (!nullSelectionAllowed && checkBoxMap.isEmpty()
                && Collections.disjoint(table.getContainerDataSource().getItemIds(), preselectedItemIds))) {
            checkBox.setInternalValue(true);
        }
        checkBoxMap.put((ID) itemId, checkBox);
        if (checkBox.getValue()) {
            fireItemSelectionChange();
        }
        return checkBox;
    }

    private void destroyCheckBoxes() {
        checkBoxMap.clear();
        updateHeaderCheckBox();
        fireItemSelectionChange();
    }

    public boolean isNullSelectionAllowed() {
        return nullSelectionAllowed;
    }

    public void setNullSelectionAllowed(boolean nullSelectionAllowed) {
        if (this.nullSelectionAllowed != nullSelectionAllowed) {
            this.nullSelectionAllowed = nullSelectionAllowed;
            if (!nullSelectionAllowed && !checkBoxMap.isEmpty() && !isAnyItemSelected()) {
                checkBoxMap.entrySet().iterator().next().getValue().setInternalValue(true);
                fireItemSelectionChange();
            }
            updateCheckboxStyle();
            updateHeaderCheckBox();
        }
    }

    public boolean isMultiselect() {
        return multiselect;
    }

    public void setMultiselect(boolean multiselect) {
        if (this.multiselect != multiselect) {
            this.multiselect = multiselect;
            if (!multiselect) {
                boolean firstFound = false;
                for (Map.Entry<ID, CheckBox> e : checkBoxMap.entrySet()) {
                    if (e.getValue().getValue()) {
                        if (firstFound) {
                            e.getValue().setInternalValue(false);
                        } else {
                            firstFound = true;
                        }
                    }
                }
                fireItemSelectionChange();
            }
            updateCheckboxStyle();
            updateHeaderCheckBox();
        }
    }

    private void updateCheckboxStyle() {
        for (CheckBox checkBox : checkBoxMap.values()) {
            if (multiselect || nullSelectionAllowed) {
                checkBox.removeStyleName(RADIO_STYLE);
            } else {
                checkBox.addStyleName(RADIO_STYLE);
            }
        }
    }

    public boolean isHeaderCheckbox() {
        return headerCheckbox;
    }

    public void setHeaderCheckbox(boolean headerCheckbox) {
        if (this.headerCheckbox != headerCheckbox) {
            this.headerCheckbox = headerCheckbox;
            updateHeaderCheckBox();
        }
    }

    private void updateHeaderCheckBox() {
        if (table != null) {
            if (headerCheckbox && multiselect && nullSelectionAllowed) {
                table.setColumnHeader(columnId, "<span class=\"v-checkbox v-widget\"><input type=\"checkbox\""
                        + (!checkBoxMap.isEmpty() && areAllItemsSelected() ? " checked" : "")
                        + "><label></label></span>");
                table.removeHeaderClickListener(headerClickListener);
                table.addHeaderClickListener(headerClickListener);
            } else {
                table.setColumnHeader(columnId, null);
                table.removeHeaderClickListener(headerClickListener);
            }
        }

    }

    public boolean isRowClickSelection() {
        return rowClickSelection;
    }

    public void setRowClickSelection(boolean rowClickSelection) {
        if (this.rowClickSelection != rowClickSelection) {
            this.rowClickSelection = rowClickSelection;
            updateRowClickSelection();
        }
    }

    private void updateRowClickSelection() {
        if (table != null) {
            if (rowClickSelection) {
                table.removeItemClickListener(itemClickListener);
                table.addItemClickListener(itemClickListener);
            } else {
                table.removeItemClickListener(itemClickListener);
            }
        }

    }

    public void selectAllItems() {
        if (multiselect) {
            for (CheckBox checkBox : checkBoxMap.values()) {
                checkBox.setInternalValue(true);
            }
            fireItemSelectionChange();
        }
    }

    public void selectNoneItems() {
        if (nullSelectionAllowed) {
            for (CheckBox checkBox : checkBoxMap.values()) {
                checkBox.setInternalValue(false);
            }
            fireItemSelectionChange();
        }
    }

    public void toggleItemSelection() {
        if (isAnyItemSelected()) {
            selectNoneItems();
        } else {
            selectAllItems();
        }
        updateHeaderCheckBox();
    }

    public void setItemSelected(ID itemId, boolean selected) {
        if (table != null) {
            CheckBox checkBox = checkBoxMap.get(itemId);
            if(checkBox != null) {
                checkBox.setValue(selected);
            }
        } else if (selected) {
            preselectedItemIds.add(itemId);
        }
    }

    public boolean isItemSelected(ID itemId) {
        CheckBox checkBox = checkBoxMap.get(itemId);
        return checkBox != null && Boolean.TRUE.equals(checkBox.getValue());
    }

    public void toggleItemSelected(ID itemId) {
        setItemSelected(itemId, !isItemSelected(itemId));
    }

    public boolean isAnyItemSelected() {
        return checkBoxMap.entrySet().stream().anyMatch(e -> e.getValue().getValue());
    }

    private boolean areAllItemsSelected() {
        return checkBoxMap.entrySet().stream().allMatch(e -> e.getValue().getValue());
    }

    public ID getFirstSelectedItemId() {
        List<ID> selectedItemIds = getSelectedItemIds();
        return selectedItemIds.isEmpty() ? null : selectedItemIds.get(0);
    }

    public List<ID> getSelectedItemIds() {
        return checkBoxMap.entrySet().stream()
                .filter(e -> e.getValue().getValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public void addSelectionChangeListener(SelectionChangeListener<ID> listener) {
        listeners.add(Objects.requireNonNull(listener));
    }

    public void removeSelectionChangeListener(SelectionChangeListener<ID> listener) {
        listeners.remove(Objects.requireNonNull(listener));
    }

    private void fireItemSelectionChange() {
        if (!listeners.isEmpty()) {
            SelectionChangeEvent<ID> event = new SelectionChangeEvent<>(ImmutableList.copyOf(getSelectedItemIds()));
            for (SelectionChangeListener<ID> listner: listeners) {
                listner.selectionChange(event);
            }
        }
    }

    public interface SelectionChangeListener<ID> {

        void selectionChange(SelectionChangeEvent<ID> event);

    }

    public static class SelectionChangeEvent<ID> {

        private List<ID> selectedItemIds;

        public SelectionChangeEvent(List<ID> selectedItemIds) {
            this.selectedItemIds = selectedItemIds;
        }

        public List<ID> getSelectedItemIds() {
            return selectedItemIds;
        }

    }

    private static class CheckBox extends com.vaadin.ui.CheckBox {

        @Override
        public void setInternalValue(Boolean newValue) {
            super.setInternalValue(newValue);
        }

    }

}
