package cz.req.ax;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author by Ondřej Buriánek, burianek@marbes.cz.
 * @since 7.9.15
 */
@Component
@ConfigurationProperties("vax")
public class AxProperties {

    private String viewMain = "Main";
    private String viewUser;
    private String viewError;
    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getViewError() {
        return viewError;
    }

    public void setViewError(String viewError) {
        this.viewError = viewError;
    }

    public String getViewMain() {
        return viewMain;
    }

    public void setViewMain(String viewMain) {
        this.viewMain = viewMain;
    }

    public String getViewUser() {
        return viewUser;
    }

    public void setViewUser(String viewUser) {
        this.viewUser = viewUser;
    }
}
