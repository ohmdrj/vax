package cz.req.ax.util;

import java.io.InputStream;
import java.util.Properties;

public class MavenUtils {

    public static String getVersion(String groupId, String artifactId) {
        String version;
        try {
            InputStream resourceAsStream = MavenUtils.class.getResourceAsStream("/META-INF/maven/cz.tacr.elza/elza-ui/pom.properties");
            Properties prop = new Properties();
            prop.load(resourceAsStream);
            version = prop.getProperty("version");
        } catch (Exception e) {
            version = "Devel";
        }
        return version;
    }

}
