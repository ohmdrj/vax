package cz.req.ax;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Michal Hájek, <a href="mailto:michal.hajek@marbes.cz">michal.hajek@marbes.cz</a>
 * @since 12. 8. 2015
 */
public class ChildComponentContainer<T, C extends Component> extends CssLayout {

    private BiMap<T, C> childs;

    /**
     * Inicializovaný kontejner.
     */
    public ChildComponentContainer() {
        childs = HashBiMap.create();
    }

    /**
     * Přidá komponentu do kontejneru.
     *
     * @param key   klíč, dle kterého bude možné komponentu v kontejneru dohledat
     * @param value komponenta
     * @return přidaná komponenta
     */
    public C addComponent(T key, C value) {
        addComponent(value);
        return childs.put(key, value);
    }

    /**
     * Odstraní komponentu předaného klíče.
     *
     * @param key klíč
     */
    public void removeComponent(T key) {
        removeComponent(childs.get(key));
        childs.remove(key);
    }

    /**
     * Přesune komponentu v kontejneru a pozici druhé komponenty.
     *
     * @param source přesouvaná komponenta
     * @param target cílová pozice/komponenta
     */
    public void moveComponent(C source, C target) {
        int index = getComponentIndex(target);
        removeComponent(source);
        addComponent(source, index);
    }

    /**
     * Přesune předanou komponentu o pozici níže.
     *
     * @param source přesouvaná komponeta
     */
    public void moveUp(C source) {
        C target = getPredchazejici(source);
        moveComponent(source, target);
    }

    /**
     * Přesune předanou komponentu o pozici nahoru.
     *
     * @param source přesouvaná komponeta
     */
    public void moveDown(C source) {
        C target = getNasledujici(source);
        moveComponent(source, target);
    }

    /**
     * Vrací všechny komponenty kontejneru.
     *
     * @return všechny konponenty
     */
    public List<C> getChils() {
        ArrayList<C> list = new ArrayList<>();
        for (int i = 0; i < getComponentCount(); i++) {
            list.add((C) getComponent(i));
        }
        return list;
    }

    /**
     * Vrací všechny klíče komponent.
     *
     * @return všechny klíče
     */
    public List<T> getKeys() {
        return getChils().stream().map(c -> childs.inverse().get(c)).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Vrací dle předané komponenty.
     *
     * @param c komponenta
     * @return klíč
     */
    public T getKey(C c) {
        return childs.inverse().get(c);
    }

    /**
     * Vrací poslední klíč.
     *
     * @return poslední klíč
     */
    public T getLastKey() {
        if (getComponentCount() == 0) return null;
        return childs.inverse().get(getComponent(getComponentCount() - 1));
    }

    /**
     * Vrací komponentu, dle předaného klíče.
     *
     * @param key klíč
     * @return komponenta
     */
    public C getComponent(T key) {
        return childs.get(key);
    }

    /**
     * Vrací následující komponentu, pokud není předaná komponenta v kontejneru právě ta poslední, pak
     * vrací právě tu (poslední).
     *
     * @param c vrací následující, nebo poslední komponentu
     * @return následující komponanta
     */
    public C getNasledujici(C c) {
        int index = getComponentIndex(c);
        if (getComponentCount() == 0) return c;
        return (C) getComponent(index + 1);
    }

    /**
     * Vrací předcházející komponentu, pokud není předaná komponenta v kontejneru právě na prvním indexu, pak
     * vrací právě tu (první).
     *
     * @param c vrací předcházející, nebo první komponentu
     * @return následující komponanta
     */
    public C getPredchazejici(C c) {
        int index = getComponentIndex(c);
        if (index == 0) return c;
        return (C) getComponent(index - 1);
    }

}
