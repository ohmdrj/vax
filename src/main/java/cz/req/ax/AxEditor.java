package cz.req.ax;

import com.vaadin.data.Property;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import cz.thickset.utils.IdObject;

/*@Component("AxEditor")
@Scope("prototype")*/
public class AxEditor<T extends IdObject<Integer>> implements RefreshListener {

    AxContainer<T> container;
    ItemForm<T> form;
    Table table;

    public AxEditor(final AxContainer<T> cont) {
        container = cont;

        form = ItemForm.init(container.getRepository().entityClass());
        form.setSizeUndefined();
        form.addButton("Uložit", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                T entity = form.commit();
                if (entity.getId() == null) {
                    form.setItem(container.create(entity));
                } else {
                    form.setItem(container.update(entity));
                }
                Notification.show("Uloženo");
                table.refreshRowCache();
                table.select(form.getValue().getId());
            }
        });
        //TODO Rozhodne lip.... +delete?
        form.addButton("Nový", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                form.setValue(container.getRepository().entityInstance());
            }
        });

        table = new Table(null, container);
        table.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
        table.setSelectable(true);
        table.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                form.setItem(container.getItem(event.getProperty().getValue()));
            }
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
        //layout.setExpandRatio(table, 1f);
        //layout.setExpandRatio(form, 1f);
        layout.setSizeFull();
        return layout;
    }

    public void refresh() {
        table.refreshRowCache();
    }

    public static interface Init<S extends IdObject<Integer>> {
        void init(AxContainer container, Table table, ItemForm<S> form);
    }

    public static interface InitSave<S extends IdObject<Integer>> extends Init<S> {
        void onSave(S entity);
    }

}
