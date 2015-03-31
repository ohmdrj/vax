package cz.req.ax;

public interface InitTableForm<S extends IdObject<Integer>> {
    void init(AxTable<S> table, AxForm<S> form);
}
