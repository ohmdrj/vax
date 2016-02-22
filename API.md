# Vaadin Ax API

## TL;DR

Čistý Vaadin:

    Button button = new Button();
    button.setCaption("OK");
    button.setIcon(FontAwesome.CHECK);
    button.addClickListener(e -> provedAkci());

Vaadin Ax:

    Button button = Ax.button("OK").icon(FA.CHECK).onClick(provedAkci).get();

## Zakladní komponenty

| Název | Popis |
|-------| ------|
| Ax.label() | Builder pro `Label` s nastavenou hodnotou |
| Ax.icon() | Builder pro `Label` s nastavenou ikonou |
| Ax.caption() | Builder pro `Label` s nastaveným popiskem |
| Ax.button() | Builder pro tlačítko `Button` |
| Ax.menuBar() | Builder pro menu bar `MenuBar` |
| Ax.menu() | Builder pro rozbalovací `MenuItem` |
| Ax.menuItem() | Builder pro koncový `MenuItem` |
| Ax.upload() | Builder pro upload `Upload` |

## Pole

| Název | Popis |
|-------| ------|
| Ax.textField() | Builder pro textové pole `TextField` |
| Ax.passwordField() | Builder pro heslové pole `PasswordField` |
| Ax.richTextArea() | Builder pro rich text `RichTextArea` |
| Ax.textArea() | Builder pro text area  `TextArea` |
| Ax.checkBox() | Builder pro check box `CheckBox` |
| Ax.comboBox() | Builder pro combo box `ComboBox` |
| Ax.optionGroup() | Builder pro radio button `OptionGroup` |
| Ax.dateTimeField() | Builder pro datumové pole s časem  `DateTimeField` |
| Ax.dateField() | Builder pro datumové pole `DateField` |
| Ax.timeField() | Builder pro časové pole `TimeField` |

## Tvorba akcí

| Název | Popis |
|-------| ------|
| Ax.action() | Builder pro akci `AxAction` |

## Databinding

| Název | Popis |
|-------| ------|
| Ax.binder() | Formulářový databinding přes `AxBinder` (rozšíření `BeanFieldGroup`) |
| Ax.container() | Builder pro kontejnery `AxBeanContainer` / `AxBeanItemContainer` |
| Ax.enumContainer() | Speciální builder pro kontejner s `enum` |

## Layout

| Název | Popis |
|-------| ------|
| Ax.cssLayout() | Builder pro `CssLayout` |

## Tabulky

| Název | Popis |
|-------| ------|
| Ax.table() | Builder pro tabulku `Table` |
| SelectionColumn | Generátor pro výběrový sloupec v tabulce |

## Tvorba oken

| Název | Popis |
|-------| ------|
| AxWindow | Základní class pro okna (pokud potřebuju oddědit) |
| Ax.window() | Builder pro okno `AxWindow` |
| Ax.windowButton() | Builder pro okenní tlačítko (pokud poteřbuju vyrobit mimo okno) |
| Ax.message() | Informační dialog. |
| Ax.confirm() | Potvrzovací dialog Ano/Ne. |

*Poznámka:* volání ukončuji `.show()`

## Pop-up notifikace

| Název | Popis |
|-------| ------|
| Ax.notify() | Obecná notifikace (3s) |
| Ax.info() | Informace (zmizí po pohybu myši) |
| Ax.warning() | Varování (1,5s) |
| Ax.error() | Chyba (uživatel musí odkliknout) |
| Ax.tray() | Notifikace vpravo dole (3s) |

*Poznámka:* volání ukončuji `.show()`

## Utility

| Název | Popis |
|-------| ------|
| Ax.defaults() | Konfigurace výchozího stavu komponent |
| Ax.navigate() | Navigace mezi view |
| Ax.polling() | Zapnutí/vypnutí pollingu UI |

## Zkratky

| Název | Popis |
|-------| ------|
| Ax.layout() | Ax.cssLayout(komponenty...).get() |
| Ax.h1() | Nadpis úrovně 1 |
| Ax.h2() | Nadpis úrovně 2 |
| Ax.h3() | Nadpis úrovně 3 |
| Ax.hr() | Oddělovací čára `<hr>` |
