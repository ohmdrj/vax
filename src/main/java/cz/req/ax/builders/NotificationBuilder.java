package cz.req.ax.builders;

import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 16.2.2016
 */
public class NotificationBuilder extends AxBuilder<Notification, NotificationBuilder> {

    public NotificationBuilder(String caption, Notification.Type type) {
        this(new Notification(caption, type), true);
    }

    public NotificationBuilder(Notification target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public NotificationBuilder caption(String caption) {
        target.setCaption(caption);
        return this;
    }

    public NotificationBuilder icon(Resource icon) {
        target.setIcon(icon);
        return this;
    }

    public NotificationBuilder description(String description) {
        target.setDescription(description);
        return this;
    }

    public NotificationBuilder delay(int msec) {
        target.setDelayMsec(msec);
        return this;
    }

    public NotificationBuilder delayForever() {
        return delay(Notification.DELAY_FOREVER);
    }

    public NotificationBuilder delayNone() {
        return delay(Notification.DELAY_NONE);
    }

    public NotificationBuilder style(String style) {
        target.setStyleName(style);
        return this;
    }

    public NotificationBuilder html() {
        target.setHtmlContentAllowed(true);
        return this;
    }

    public NotificationBuilder position(Position position) {
        target.setPosition(position);
        return this;
    }

    public NotificationBuilder topLeft() {
        return position(Position.TOP_LEFT);
    }

    public NotificationBuilder topCenter() {
        return position(Position.TOP_CENTER);
    }

    public NotificationBuilder topRight() {
        return position(Position.TOP_RIGHT);
    }

    public NotificationBuilder middleLeft() {
        return position(Position.MIDDLE_LEFT);
    }

    public NotificationBuilder middleCenter() {
        return position(Position.MIDDLE_CENTER);
    }

    public NotificationBuilder middleRight() {
        return position(Position.MIDDLE_RIGHT);
    }

    public NotificationBuilder bottomLeft() {
        return position(Position.BOTTOM_LEFT);
    }

    public NotificationBuilder bottomCenter() {
        return position(Position.BOTTOM_CENTER);
    }

    public NotificationBuilder bottomRight() {
        return position(Position.BOTTOM_RIGHT);
    }

    public void show(Page page) {
        target.show(page);
    }

    public void show() {
        target.show(Page.getCurrent());
    }

}
