package cz.req.ax;

import com.vaadin.data.util.BeanItem;
import cz.thickset.utils.IdObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("AxContainer")
@Scope("prototype")
public class AxContainer<T extends IdObject<Integer>> extends AxBeanContainer<T> {

    private AxRepository<T> repository;

    public AxContainer(AxRepository<T> repository) throws IllegalArgumentException {
        super(repository.entityClass());
        this.repository = repository;
        setBeanIdResolver(new BeanIdResolver<Integer, T>() {
            @Override
            public Integer getIdForBean(T bean) {
                if (bean == null) return null;
                return bean.getId();
            }
        });
        refresh();
    }

    public AxRepository<T> getRepository() {
        return repository;
    }


    public BeanItem<T> create(T bean) {
        T item = repository.save(bean);
        return addItem(item.getId(), item);
    }

    public BeanItem<T> update(T bean) {
        T item = repository.save(bean);
        return getItem(item.getId());
    }

    public Integer delete(Object object) {
        Integer id = super.delete(object);
        repository.delete(id);
        return id;
    }

    public void refresh() {
        removeAllItems();
        addAll(repository.findAll());
    }

}
