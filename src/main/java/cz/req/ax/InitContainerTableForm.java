package cz.req.ax;

public interface InitContainerTableForm<S extends IdObject<Integer>> {

    void init(AxContainer<S> container, AxTable<S> table, AxForm<S> form);

}
