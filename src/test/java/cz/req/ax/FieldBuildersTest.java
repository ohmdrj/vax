package cz.req.ax;

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
        binder.addTextArea("description").rows(5);
        binder.addField("check");
        AxFormLayout layout = binder.createForm();
    }

}
