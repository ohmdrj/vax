package cz.req.ax;

import com.vaadin.data.util.BeanItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Component("AxContainer")
@Scope("prototype")
public class AxContainer<T extends IdObject<Integer>> extends AxBeanContainer<T> implements Refresh {

    private AxRepository<T> repository;
    private Supplier<List<T>> supplier;
    private Function<AxRepository<T>, List<T>> function;
    private BeanIdResolver<Integer, T> resolver = bean -> bean == null ? null : bean.getId();

    public AxContainer(AxRepository<T> repository) throws IllegalArgumentException {
        super(repository.entityClass());
        this.repository = repository;
        setBeanIdResolver(this.resolver);
    }

    public void repository(AxRepository<T> repository) {
        this.repository = repository;
    }

    public AxRepository<T> getRepository() {
        return repository;
    }

    public AxContainer<T> supplier(Supplier<List<T>> supplier) {
        this.supplier = supplier;
        return this;
    }

    public AxContainer<T> supplier(Function<AxRepository<T>, List<T>> function) {
        this.function = function;
        return this;
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
        if (function != null) {
            addAll(function.apply(repository));
        } else if (supplier != null) {
            addAll(supplier.get());
        } else {
            addAll(repository.findAll());
        }
    }

    public void replaceAllItems(List<T> items) {
        removeAllItems();
        if (items != null) addAll(items);
    }
}
