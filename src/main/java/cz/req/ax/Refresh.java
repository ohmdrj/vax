package cz.req.ax;

import com.vaadin.ui.UI;

@FunctionalInterface
public interface Refresh {

    void refresh();

    static void tryRefresh(Object o) {
        if (o instanceof Refresh) {
            ((Refresh) o).refresh();
        }
    }

    /*default void pushRefresh() {
        if (getUI() == null) return;
        getUI().access(() -> {
            try {
                refresh();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }*/

}
