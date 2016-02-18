package cz.req.ax.util;

import com.google.common.base.Strings;
import com.vaadin.event.ShortcutAction;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import cz.req.ax.AxUtils;
import cz.req.ax.ui.AxWindow;
import cz.req.ax.ui.AxWindowButton;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 18.2.2016
 */
public class AxDefaults {

    private static AxDefaults instance;

    public static AxDefaults getInstance() {
        if (instance == null) {
            instance = new AxDefaults();
        }
        return instance;
    }

    private Map<Class<?>, Consumer<Object>> configurers = new HashMap<>();

    // TODO message.properties
    public String requiredError = "Není vyplněna povinná hodnota.";
    public String invalidValueError = "Byla zadána neplatná hodnota.";
    public String captionSuffix = ":";

    private AxDefaults() {
        define(AbstractComponent.class, component -> {
            component.setImmediate(true);
        });
        define(Label.class, label -> {
            label.setSizeUndefined();
        });
        define(CssLayout.class, layout -> {
            layout.setSizeUndefined();
        });
        define(Field.class, field -> {
            field.setRequiredError(requiredError);
        });
        define(AbstractField.class, field -> {
            field.setConversionError(invalidValueError);
        });
        define(AbstractTextField.class, field -> {
            field.setNullSettingAllowed(false);
            field.setNullRepresentation("");
        });
        define(RichTextArea.class, area -> {
            area.setNullSettingAllowed(false);
            area.setNullRepresentation("");
        });
        define(AbstractSelect.class, select -> {
            select.setNullSelectionAllowed(true);
            select.setNewItemsAllowed(false);
        });
        define(AbstractSelect.Filtering.class, select -> {
            select.setFilteringMode(FilteringMode.CONTAINS);
        });
        define(Upload.class, upload -> {
            upload.setCaption("Nahrát soubor");
        });
        define(Window.class, window -> {
            window.setModal(true);
            window.setDraggable(false);
            window.setClosable(false);
            window.setResizable(false);
            window.setCloseShortcut(ShortcutAction.KeyCode.ESCAPE);
            window.center();
        });
        define(AxWindow.class, window -> {
            window.setAutoFocusMode(AxWindow.AutoFocusMode.FIRST_FIELD_OR_WINDOW);
            window.addCloseButton();
        });
        define(AxWindowButton.class, button -> {
            button.setAlignment(AxWindowButton.Alignment.RIGHT);
        });
    }

    public String getRequiredError() {
        return requiredError;
    }

    public String getInvalidValueError() {
        return invalidValueError;
    }

    public String getCaptionSuffix() {
        return captionSuffix;
    }

    public void clear() {
        configurers.clear();
    }

    public <T> void define(Class<T> type, Consumer<T> configurer) {
        configurers.put(type, (Consumer<Object>) configurer);
    }

    public <T> T apply(T object) {
        if (object == null) {
            return null;
        }
        Class<?> objectType = object.getClass();
        configurers.forEach((type, configurer) -> {
            if (type.isAssignableFrom(objectType)) {
                configurer.accept(object);
            }
        });
        return object;
    }

}
