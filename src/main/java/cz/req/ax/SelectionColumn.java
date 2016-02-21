package cz.req.ax;

import com.google.common.collect.ImmutableList;
import com.vaadin.data.Container;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @deprecated moved to {@link cz.req.ax.ui.SelectionColumn}
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 18.1.2016
 */
@Deprecated
public class SelectionColumn<ID> extends cz.req.ax.ui.SelectionColumn<ID> {

    public SelectionColumn() {
    }

    public SelectionColumn(String columnId) {
        super(columnId);
    }

}
