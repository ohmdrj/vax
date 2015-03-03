package cz.req.ax;

@FunctionalInterface
public interface Refresh {

    void refresh();

    static void tryRefresh(Object o) {
        if (o instanceof Refresh) {
            ((Refresh) o).refresh();
        }
    }
}
