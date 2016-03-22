package cz.req.ax.util;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import cz.req.ax.ui.AxWindow;
import cz.req.ax.ui.AxWindowButton;

import java.util.ArrayList;
import java.util.List;
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

    private List<Configurer> configurers = new ArrayList<>();

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
        define(Field.class, field -> {
            field.setRequiredError(requiredError);
        });
        define(AbstractField.class, field -> {
            field.setConversionError(invalidValueError);
        });
        define(AbstractTextField.class, field -> {
            field.setNullRepresentation("");
        });
        define(RichTextArea.class, area -> {
            area.setNullRepresentation("");
        });
        define(AbstractSelect.Filtering.class, select -> {
            select.setFilteringMode(FilteringMode.CONTAINS);
        });
        define(Upload.class, upload -> {
            upload.setButtonCaption("Nahrát soubor");
        });
        define(Table.class, table -> {
            table.setColumnHeaderMode(Table.ColumnHeaderMode.EXPLICIT);
            table.setWidth(100, Sizeable.Unit.PERCENTAGE);
            table.setPageLength(0);
        });
        define(AbstractOrderedLayout.class, layout -> {
            layout.setMargin(false);
            layout.setSizeUndefined();
        });
        define(Window.class, window -> {
            window.setModal(true);
            window.setDraggable(false);
            window.setClosable(false);
            window.setResizable(false);
            window.setCloseShortcut(ShortcutAction.KeyCode.ESCAPE);
            window.center();
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

    public void clearAll() {
        configurers.clear();
    }

    public void clear(Class<?> type) {
        configurers.removeIf(c -> c.type.equals(type));
    }

    public <T> void redefine(Class<T> type, Consumer<T> configurer) {
        clear(type);
        define(type, configurer);
    }

    public <T> void define(Class<T> type, Consumer<T> configurer) {
        configurers.add(new Configurer(type, configurer));
    }

    public <T> T apply(T object) {
        if (object == null) {
            return null;
        }
        Class<?> objectType = object.getClass();
        for (Configurer configurer: configurers) {
            if (configurer.type.isAssignableFrom(objectType)) {
                configurer.callback.accept(object);
            }
        }
        return object;
    }

    private static class Configurer<T> {

        private Class<T> type;
        private Consumer<T> callback;

        public Configurer(Class<T> type, Consumer<T> callback) {
            this.type = type;
            this.callback = callback;
        }

    }

}
