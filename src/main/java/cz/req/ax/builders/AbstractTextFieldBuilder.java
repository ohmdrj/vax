package cz.req.ax.builders;

import com.vaadin.ui.AbstractTextField;
import cz.req.ax.AxBinder;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class AbstractTextFieldBuilder<F extends AbstractTextField> extends FieldBuilder<String, F, AbstractTextFieldBuilder<F>> {

    public AbstractTextFieldBuilder(F field) {
        super(field);
    }



    public AbstractTextFieldBuilder<F> maxLength(Integer length) {
        field.setMaxLength(length);
        return this;
    }

}
