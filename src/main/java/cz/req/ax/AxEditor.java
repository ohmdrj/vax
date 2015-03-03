package cz.req.ax;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;

public class AxEditor<T extends IdObject<Integer>> implements Refresh {

    AxContainer<T> container;
    AxForm<T> form;
    Table table;

    public AxEditor(final AxContainer<T> cont) {
        container = cont;

        form = AxForm.init(container.getRepository().entityClass());
        form.setSizeUndefined();
        form.addButton("Uložit", event -> {
            T entity = form.commit();
            if (entity.getId() == null) {
                form.setItem(container.create(entity));
            } else {
                form.setItem(container.update(entity));
            }
            Notification.show("Uloženo");
            table.refreshRowCache();
            table.select(form.getValue().getId());
        });
        //TODO Rozhodne lip.... +delete?
        form.addButton("Nový", event -> {
            form.setValue(container.getRepository().entityInstance());
        });

        table = new Table(null, container);
        table.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
        table.setSelectable(true);
        table.addValueChangeListener(event -> {
            Object value = event.getProperty().getValue();
            form.setItem(value == null ? null : container.getItem(value));
        });

    }

    public AbstractComponentContainer initHorizontal(Init init) {
        init.init(container, table, form);
        table.setSizeFull();
        HorizontalLayout layout = new HorizontalLayout(table, form);
        layout.setSizeFull();
        return layout;
    }

    public AbstractComponentContainer initVertical(Init init) {
        init.init(container, table, form);
        table.setPageLength(0);
        table.setWidth(100, Sizeable.Unit.PERCENTAGE);
        VerticalLayout layout = new VerticalLayout(table, form);
        layout.setSizeFull();
        return layout;
    }

    public void refresh() {
        table.refreshRowCache();
    }

    public static interface Init<S> {
        void init(AxContainer container, Table table, AxForm<S> form);
    }
}
