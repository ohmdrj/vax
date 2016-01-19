package cz.req.ax;

import com.google.common.collect.ImmutableList;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 18.1.2016
 */
public class SelectionColumn<ID> implements Table.ColumnGenerator, AxTable.IdCellGenerator<ID> {

    public static final String ID = "_selection";
    private static final String RADIO = "radio";

    private boolean multiselect;
    private boolean nullSelectionAllowed;
    private Map<ID, CheckBox> checkBoxMap = new HashMap<>();
    private Set<SelectionChangeListener<ID>> listeners = new LinkedHashSet<>();

    @Override
    public Object generateCell(Table source, Object itemId, Object columnId) {
        return generateCell((ID) itemId, columnId);
    }

    @Override
    public Component generateCell(ID itemId, Object columnId) {
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
            checkBox.addStyleName(RADIO);
        }
        if (!nullSelectionAllowed && checkBoxMap.isEmpty()) {
            checkBox.setInternalValue(true);
        }
        checkBoxMap.put(itemId, checkBox);
        return checkBox;
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
        }
    }

    private void updateCheckboxStyle() {
        for (CheckBox checkBox : checkBoxMap.values()) {
            if (multiselect || nullSelectionAllowed) {
                checkBox.removeStyleName(RADIO);
            } else {
                checkBox.addStyleName(RADIO);
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
    }

    public void setItemSelected(ID itemId, boolean selected) {
        CheckBox checkBox = checkBoxMap.get(itemId);
        if(checkBox != null) {
            checkBox.setValue(selected);
        }
    }

    public boolean isItemSelected(ID itemId) {
        CheckBox checkBox = checkBoxMap.get(itemId);
        return checkBox != null && Boolean.TRUE.equals(checkBox.getValue());
    }

    public boolean isAnyItemSelected() {
        return checkBoxMap.entrySet().stream().anyMatch(e -> e.getValue().getValue());
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
