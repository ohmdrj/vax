package cz.req.ax;

import com.vaadin.data.util.BeanItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class AxContainer<T> extends AxBeanContainer<T> implements Refresh {

    private JpaRepository<T, Integer> repository;
    private Supplier<List<T>> supplier;
    private Function<JpaRepository<T, Integer>, List<T>> function;
    private BeanIdResolver<Integer, T> resolver = bean -> ObjectIdentity.id(bean);

    public static <T> AxContainer<T> init(Class<T> type) {
        return new AxContainer<>(type);
    }

    public AxContainer(AxRepository<T> repository) {
        this(repository.entityClass());
        repository(repository);
    }

    public AxContainer(Class<T> entityClass) {
        super(entityClass);
        setBeanIdResolver(this.resolver);
    }

    public JpaRepository<T, Integer> getRepository() {
        return repository;
    }

    public AxContainer<T> repository(JpaRepository<T, Integer> repository) {
        this.repository = repository;
        return this;
    }

    public AxContainer<T> supplier(Supplier<List<T>> supplier) {
        this.supplier = supplier;
        return this;
    }

    public AxContainer<T> supplier(Function<JpaRepository<T, Integer>, List<T>> function) {
        this.function = function;
        return this;
    }

    public BeanItem<T> create(T bean) {
        T item = repository.save(bean);
        return addItem(ObjectIdentity.id(bean), item);
    }

    public BeanItem<T> update(T bean) {
        Integer itemId = ObjectIdentity.id(bean);
        T updatedBean = repository.save(bean);
        int index = indexOfId(itemId);
        removeItem(itemId);
        return addBeanAt(index, updatedBean);
    }

    public Integer delete(Object object) {
        repository.delete(getId(object));
        return super.delete(object);
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
