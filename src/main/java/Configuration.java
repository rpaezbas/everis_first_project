

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * This class is the main route for the REST service, the annotation
 * "ApplicationPath" sets the root path in the url.
 *
 * @author Rafael Paez
 * @version 1.0
 * @since 2018-05-03
 */

// This is the de REST services' s path.
@ApplicationPath("/api")
public class Configuration extends Application {

}
