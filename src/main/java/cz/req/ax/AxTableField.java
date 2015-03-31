package cz.req.ax;

public class AxTableField {

    AxItemTable table;
    AxForm form;

    public AxTableField(Class clazz, InitTableForm init) {
        AxItemContainer container = AxItemContainer.init(clazz);
        table = AxItemTable.init(container);
        form = AxForm.init(clazz);
        init.init(table, form);
    }
}
