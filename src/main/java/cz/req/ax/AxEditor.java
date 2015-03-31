package cz.req.ax;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class AxEditor<T extends IdObject<Integer>> implements Refresh {

    AxContainer<T> container;
    AxForm<T> form;
    AxTable<T> table;

    public static <T extends IdObject<Integer>> AxEditor<T> init(AxContainer<T> cont) {
        return new AxEditor<T>(cont);
    }

    private AxEditor(final AxContainer<T> cont) {
        container = cont;

        form = AxForm.init(container.getRepository().entityClass());
        form.setSizeUndefined();
        form.getButtonBar().addComponent(new AxAction<T>().caption("Uložit").primary()
                .run(() -> {
                    //TODO fix value??? .value(form.commit()).action(entity -> {
                    T entity = form.commit();
                    if (entity.getId() == null) {
                        form.setItem(container.create(entity));
                    } else {
                        form.setItem(container.update(entity));
                    }
                    Notification.show("Uloženo");
                    table.refresh();
                    table.getTable().select(form.getValue().getId());
                }).button());
        form.getButtonBar().addComponent(new AxAction<T>().caption("Nový").action(entity -> {
            form.setValue(container.getRepository().entityInstance());
        }).button());
        //TODO delete action

        table = new AxBeanTable<>(container);
        table.getTable().addValueChangeListener(event -> {
            Object value = event.getProperty().getValue();
            form.setItem(value == null ? null : container.getItem(value));
        });

        table.refresh();
    }

    public AbstractComponentContainer initHorizontal(InitContainerTableForm<T> init) {
        init.init(container, table, form);
        table.getTable().setSizeFull();
        HorizontalLayout layout = new HorizontalLayout(table.getTable(), form);
        layout.setSizeFull();
        return layout;
    }

    public AbstractComponentContainer initVertical(InitContainerTableForm<T> init) {
        init.init(container, table, form);
        table.getTable().setPageLength(0);
        table.getTable().setWidth(100, Sizeable.Unit.PERCENTAGE);
        VerticalLayout layout = new VerticalLayout(table.getTable(), form);
        layout.setSizeFull();
        return layout;
    }

    public void refresh() {
        table.refresh();
    }

}
