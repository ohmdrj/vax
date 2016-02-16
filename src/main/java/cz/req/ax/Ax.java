package cz.req.ax;

import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import cz.req.ax.builders.*;
import cz.req.ax.data.AxBinder;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 13.2.2016
 */
public class Ax {

    public static AxActionBuilder<Object> action() {
        return new AxActionBuilder<>();
    }

    public static AxActionBuilder<Object> action(String caption) {
        return action().caption(caption);
    }

    public static <T> AxActionBuilder<T> action(T input) {
        return action().input(input);
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
        return button().caption(caption);
    }

    public static ButtonBuilder button(Resource icon) {
        return button().icon(icon);
    }

    public static CheckBoxBuilder checkBox() {
        return new CheckBoxBuilder();
    }

    public static LabelBuilder caption(String caption) {
        return label().caption(caption);
    }

    public static CheckBoxBuilder checkBox(String caption) {
        return checkBox().caption(caption);
    }

    public static ComboBoxBuilder comboBox() {
        return new ComboBoxBuilder();
    }

    public static ComboBoxBuilder comboBox(String caption) {
        return comboBox().caption(caption);
    }

    public static CssLayoutBuilder cssLayout(Component... components) {
        return new CssLayoutBuilder().add(components);
    }

    public static DateFieldBuilder dateField() {
        return new DateFieldBuilder();
    }

    public static DateFieldBuilder dateField(String caption) {
        return dateField().caption(caption);
    }

    public static DateTimeFieldBuilder dateTimeField() {
        return new DateTimeFieldBuilder();
    }

    public static DateTimeFieldBuilder dateTimeField(String caption) {
        return dateTimeField().caption(caption);
    }

    public static Label hr() {
        return label("<hr>").html().widthFull().get();
    }

    public static LabelBuilder icon(Resource icon) {
        return label().icon(icon);
    }

    public static LabelBuilder label() {
        return new LabelBuilder();
    }

    public static LabelBuilder label(String value) {
        return label().value(value);
    }

    public static CssLayout layout(Component... components) {
        return cssLayout(components).get();
    }

    public static MenuItemBuilder menuItem(MenuBar menuBar) {
        return new MenuItemBuilder(menuBar);
    }

    public static MenuItemBuilder menuItem(MenuBar.MenuItem parentItem) {
        return new MenuItemBuilder(parentItem);
    }

    public static AxMessageBuilder message(String message) {
        return new AxMessageBuilder(message);
    }

    public static OptionGroupBuilder optionGroup() {
        return new OptionGroupBuilder();
    }

    public static OptionGroupBuilder optionGroup(String caption) {
        return optionGroup().caption(caption);
    }

    public static PasswordFieldBuilder passwordField() {
        return new PasswordFieldBuilder();
    }

    public static PasswordFieldBuilder passwordField(String caption) {
        return passwordField().caption(caption);
    }

    public RichTextAreaBuilder richTextArea() {
        return new RichTextAreaBuilder();
    }

    public RichTextAreaBuilder richTextArea(String caption) {
        return richTextArea().caption(caption);
    }

    public static TextAreaBuilder textArea() {
        return new TextAreaBuilder();
    }

    public static TextAreaBuilder textArea(String caption) {
        return textArea().caption(caption);
    }

    public static TextFieldBuilder textField() {
        return new TextFieldBuilder();
    }

    public static TextFieldBuilder textField(String caption) {
        return textField().caption(caption);
    }

    public static TimeFieldBuilder timeField() {
        return new TimeFieldBuilder();
    }

    public static TimeFieldBuilder timeField(String caption) {
        return timeField().caption(caption);
    }

    public static UploadBuilder upload() {
        return new UploadBuilder();
    }

    public static UploadBuilder upload(String caption) {
        return upload().caption(caption);
    }

    public static AxWindowBuilder window() {
        return new AxWindowBuilder();
    }

    public static AxWindowBuilder window(String caption) {
        return window().caption(caption);
    }

    public static AxWindowButtonBuilder windowButton() {
        return new AxWindowButtonBuilder();
    }

    public static AxWindowButtonBuilder windowButton(String caption) {
        return windowButton().caption(caption);
    }

}
