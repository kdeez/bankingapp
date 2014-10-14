package rest.server.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;

public class JettyServer
{

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		Resource jettyConfig = Resource.newResource("jetty.xml");
		XmlConfiguration configuration = new XmlConfiguration(jettyConfig.getInputStream());
		Server server = (Server) configuration.configure();

		WebAppContext context = new WebAppContext();
		context.setDescriptor("WEB-INF/web.xml");
		context.setResourceBase("WEB-INF");
		context.setContextPath("/");
		context.setCopyWebInf(false);
		context.setParentLoaderPriority(true);
		server.setHandler(context);

		server.start();
		server.join();
	}

}
