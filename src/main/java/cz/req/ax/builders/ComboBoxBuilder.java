package cz.req.ax.builders;

import com.vaadin.ui.ComboBox;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class ComboBoxBuilder extends AbstractSelectBuilder<ComboBox> {

    public ComboBoxBuilder() {
        super(new ComboBox());
    }

    public ComboBoxBuilder(ComboBox field) {
        super(field);
    }

}
