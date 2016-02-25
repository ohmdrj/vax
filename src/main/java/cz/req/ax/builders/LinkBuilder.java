package cz.req.ax.builders;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.Link;

import java.net.URL;

/**
 * @author <a href="mailto:jan.pikl@marbes.cz">Jan Pikl</a>
 *         Date: 25.2.2016
 */
public class LinkBuilder extends ComponentBuilder<Link, LinkBuilder> {

    public LinkBuilder() {
        this(new Link(), true);
    }

    public LinkBuilder(Link target, boolean useDefaults) {
        super(target, useDefaults);
    }

    public LinkBuilder resource(Resource resource) {
        target.setResource(resource);
        return this;
    }

    public LinkBuilder url(String url) {
        return resource(new ExternalResource(url));
    }

    public LinkBuilder url(URL url) {
        return resource(new ExternalResource(url));
    }

    public LinkBuilder targetName(String name) {
        target.setTargetName(name);
        return this;
    }

    public LinkBuilder targetWidth(int width) {
        target.setTargetWidth(width);
        return this;
    }

    public LinkBuilder targetHeight(int height) {
        target.setTargetHeight(height);
        return this;
    }

}
