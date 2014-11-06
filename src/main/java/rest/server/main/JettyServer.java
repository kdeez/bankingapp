package rest.server.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;

/**
 * 
 * @author roger.hagen
 * 
 * Main class for the entire application.
 * 
 * jetty.xml is a server configuration equivalent to an Apache .conf file where
 * handlers, connectors are configured
 * 
 * web.xml is another server configuration file equivalent to installing mods or 
 * filters on an Apache server.  Extra plugins can be added here.
 * 
 * The WEB-INF directory is the web root can be thought of as the root directory on 
 * a unix machine.
 *
 */
public class JettyServer
{
	public static final String JETTY_CONFIG = "WEB-INF/jetty.xml";
	public static final String DESCRIPTOR_PATH = "WEB-INF/web.xml";
	public static final String RESOURCE_BASE = "WEB-INF/web-root/";
	public static final String ROOT_CONTEXT = "/";

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		Resource jettyConfig = Resource.newResource(JETTY_CONFIG);
		XmlConfiguration configuration = new XmlConfiguration(jettyConfig.getInputStream());
		Server server = (Server) configuration.configure();

		WebAppContext context = new WebAppContext();
		context.setDescriptor(DESCRIPTOR_PATH);
		context.setResourceBase(RESOURCE_BASE);
		context.setContextPath(ROOT_CONTEXT);
		context.setCopyWebInf(false);
		context.setParentLoaderPriority(true);
		server.setHandler(context);

		server.start();
	}

}
