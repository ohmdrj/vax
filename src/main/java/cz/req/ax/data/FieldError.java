package cz.req.ax.data;

import com.vaadin.ui.Field;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 23.3.2016
 */
public class FieldError {

    private Field<?> field;
    private String message;

    public FieldError(Field<?> field, String message) {
        this.field = field;
        this.message = message;
    }

    public Field<?> getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

}
