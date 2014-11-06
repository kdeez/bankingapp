package rest.server.test;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.ws.rs.core.Response.Status;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class WebTestIT 
{
	private static final Logger logger = LoggerFactory.getLogger(WebTestIT.class);
	private static int JETTY_PORT = 8081;
	private String JETTY_HOME = "http://localhost:" + JETTY_PORT + "/";
	
	@BeforeClass
	public static void sysProp() throws Exception 
	{
		//starts the web server using properties that were created for testing
		String properties = System.getProperty("user.dir") + "/src/test/resources/config.properties";
		rest.server.main.JettyServer.main(new String[]{properties});
		
		//TODO: RLH, first need to run the db.sql script on startup
	}
	
	@Before
	public void setup() 
	{
		
	}
	
	/**
	 * Parses an xml document (can use for well formed XHTML)
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static Document parse(String xml) throws Exception
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		document.getDocumentElement().normalize();
		return document;
	}
	
	/**
	 * A logged out user should be redirected to the login page if they attempt to access the dashboard (index.jsp)
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 */
	@Test
	public void testLoginRedirect() throws Exception
	{
		HttpClient client = HttpClients.createDefault();
		HttpClientContext context = HttpClientContext.create();
		HttpRequestBase request = new HttpGet(JETTY_HOME);
		HttpResponse httpResponse = client.execute( request , context);
		
		assertTrue("Should have recieved a HTTP 200 response", httpResponse.getStatusLine().getStatusCode() == Status.OK.getStatusCode());
		String response = EntityUtils.toString( httpResponse.getEntity() );
		logger.info("RESPONSE: " + response);
		
		String contentType = httpResponse.getEntity().getContentType().getValue();
		assertTrue("Incorrect mime type", contentType.split(";")[0].equals("text/html"));
		
		List<URI> redirects = context.getRedirectLocations();
		assertTrue("Should have been redirected at least once", ! redirects.isEmpty());
		URI uri = redirects.get(0);
		assertTrue("Should have been redirected to login page", uri.getPath().split(";")[0].equals("/user/login.jsp"));
	}
	
	@Test
	public void testLoginFailure()
	{
		
	}
	
	@Test
	public void testLoginSuccess() throws Exception
	{
		HttpClient client = HttpClients.createDefault();
		HttpClientContext context = HttpClientContext.create();
		HttpPost request = new HttpPost(JETTY_HOME + "/user/login.jsp");
		request.setHeader("Content-Type", "application/json");
		
		StringEntity json = new StringEntity("{'username':admin, 'password':password}");
		request.setEntity(json);
		
		HttpResponse httpResponse = client.execute( request , context);
		String response = EntityUtils.toString( httpResponse.getEntity() );
		logger.info("RESPONSE: " + response);
		
	}
	
	
	@AfterClass
	public static void shutdown() throws Exception 
	{
		logger.info("Shutting down...");
	}
}
