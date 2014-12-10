package cz.req.ax;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;

public class Tiler<T extends IdEntity> extends CssLayout
        implements RefreshListener, Button.ClickListener {

    String entityName;
    AxContainer<T> container;
    String propertyId;

    public Tiler(AxContainer<T> container, String propertyId) {
        setStyleName("tiler");
        setPropertyId(propertyId);
        setContainer(container);
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public AxContainer<T> getContainer() {
        return container;
    }

    public void setContainer(AxContainer<T> container) {
        this.container = container;
        entityName = container.getRepository().entityClass().getSimpleName();
        refresh();
    }

    public void refresh() {
        removeAllComponents();
        for (Integer itemId : container.getItemIds()) {
            BeanItem<T> beanItem = container.getItem(itemId);
            addComponent(addTileComponent(beanItem));
        }
    }

    public AbstractComponent addTileComponent(BeanItem<T> beanItem) {
        Object caption = beanItem.getItemProperty(propertyId).getValue();
        Object identity = beanItem.getItemProperty("id").getValue();
        AbstractComponent component = addTileComponent(
                caption.toString(), identity);

        component.setId(entityName + "_" + identity.toString());
        if (beanItem.getBean() instanceof StyleAware) {
            component.addStyleName(((StyleAware) beanItem.getBean()).getStyleName());
        }
        onAddTile(beanItem, component);
        return component;
    }

    public AbstractComponent addTileComponent(String caption, Object identity) {
        Button button = new Button(caption, this);
        button.setStyleName("tile");
        button.setData(identity);
        return button;

    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        Object data = event.getButton().getData();
        BeanItem<T> beanItem = container.getItem(data);
        onClick(beanItem);
    }

    public void onAddTile(BeanItem<T> beanItem, AbstractComponent component) {
    }

    public void onClick(BeanItem<T> beanItem) {
    }

}
