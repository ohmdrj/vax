package cz.req.ax;

public interface InitContainerTableForm<S> {

    void init(AxContainer<S> container, AxTable<S> table, AxForm<S> form);

}
