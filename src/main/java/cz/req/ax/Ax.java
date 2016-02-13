package cz.req.ax;

import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import cz.req.ax.builders.*;
import cz.req.ax.data.AxBinder;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 13.2.2016
 */
public class Ax {

    public static AxAction action() {
        return new AxAction();
    }

    public static AxAction action(String caption) {
        return new AxAction().caption(caption);
    }

    public static <T> AxAction action(Class<T> type) {
        return new AxAction<>();
    }

    public static <T> AxBinder<T> binder(Class<T> beanType) {
        return new AxBinder<>(beanType);
    }

    public static <T> AxBinder<T> binder(T bean) {
        return new AxBinder<>(bean);
    }

    public static <T> AxBinder<T> binder(BeanItem<T> beanItem) {
        return new AxBinder<>(beanItem);
    }

    public static ButtonBuilder button() {
        return new ButtonBuilder();
    }

    public static ButtonBuilder button(String caption) {
        return new ButtonBuilder().caption(caption);
    }

    public static CheckBoxBuilder checkBox() {
        return new CheckBoxBuilder();
    }

    public static CheckBoxBuilder checkBox(String caption) {
        return new CheckBoxBuilder().caption(caption);
    }

    public static ComboBoxBuilder comboBox() {
        return new ComboBoxBuilder();
    }

    public static ComboBoxBuilder comboBox(String caption) {
        return new ComboBoxBuilder().caption(caption);
    }

    public static CssLayoutBuilder cssLayout(Component... components) {
        return new CssLayoutBuilder().add(components);
    }

    public static DateFieldBuilder dateField() {
        return new DateFieldBuilder();
    }

    public static DateFieldBuilder dateField(String caption) {
        return new DateFieldBuilder().caption(caption);
    }

    public static DateTimeFieldBuilder dateTimeField() {
        return new DateTimeFieldBuilder();
    }

    public static DateTimeFieldBuilder dateTimeField(String caption) {
        return new DateTimeFieldBuilder().caption(caption);
    }

    public static LabelBuilder label() {
        return new LabelBuilder();
    }

    public static LabelBuilder label(String caption) {
        return new LabelBuilder().caption(caption);
    }

    public static LabelBuilder label(Resource icon) {
        return label().icon(icon);
    }

    public static CssLayout layout(Component... components) {
        return cssLayout(components).get();
    }

    public static OptionGroupBuilder optionGroup() {
        return new OptionGroupBuilder();
    }

    public static OptionGroupBuilder optionGroup(String caption) {
        return new OptionGroupBuilder().caption(caption);
    }

    public static PasswordFieldBuilder passwordField() {
        return new PasswordFieldBuilder();
    }

    public static PasswordFieldBuilder passwordField(String caption) {
        return new PasswordFieldBuilder().caption(caption);
    }

    public RichTextAreaBuilder richTextArea() {
        return new RichTextAreaBuilder();
    }

    public RichTextAreaBuilder richTextArea(String caption) {
        return new RichTextAreaBuilder().caption(caption);
    }

    public static TextAreaBuilder textArea() {
        return new TextAreaBuilder();
    }

    public static TextAreaBuilder textArea(String caption) {
        return new TextAreaBuilder().caption(caption);
    }

    public static TextFieldBuilder textField() {
        return new TextFieldBuilder();
    }

    public static TextFieldBuilder textField(String caption) {
        return new TextFieldBuilder().caption(caption);
    }

    public static TimeFieldBuilder timeField() {
        return new TimeFieldBuilder();
    }

    public static TimeFieldBuilder timeField(String caption) {
        return new TimeFieldBuilder().caption(caption);
    }

}
