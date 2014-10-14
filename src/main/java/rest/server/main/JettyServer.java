package rest.server.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;

public class JettyServer
{
	public static final String JETTY_CONFIG = "jetty.xml";
	public static final String DESCRIPTOR_PATH = "WEB-INF/web.xml";
	public static final String RESOURCE_BASE = "WEB-INF";
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
		server.join();
	}

}
