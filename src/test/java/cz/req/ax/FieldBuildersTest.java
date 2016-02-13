package cz.req.ax;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 7.2.2016
 */
public class FieldBuildersTest {

    public class TestObj {

        String name;
        String description;
        Boolean check;

    }

    public static void main(String[] args) {
        AxBinder binder = AxBinder.init(TestObj.class);

        binder.addText("name").required().maxLength(23).value("Test");
        binder.addField("check");
        binder.addTextArea("description").rows(5);

        AxFormLayout layout = binder.createForm();
    }

    public static void main2(String[] args) {
        AxBinder binder = AxBinder.init(TestObj.class);

        TextField nameField = binder.addText("name").required().maxLength(23).value("Test").field();
        Field checkField = binder.addField("check").field();
        TextArea descField = binder.addTextArea("description").rows(5).field();

        AxFormLayout layout = new AxFormLayout();
        layout.addRow().caption("Name / check").component(new CssLayout(nameField, checkField));
        layout.addRow(descField);
    }

}
