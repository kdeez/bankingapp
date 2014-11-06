package rest.server.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebTestIT 
{
	private static final Logger logger = LoggerFactory.getLogger(WebTestIT.class);
	private static int JETTY_PORT = 8081;
	private String JETTY_HOME = "http://localhost:" + JETTY_PORT + "/";
	
	@Before
	public void setup() throws Exception 
	{
		//starts the web server
		rest.server.main.JettyServer.main(new String[]{});
	}
	
	@BeforeClass
	public static void sysProp() 
	{

	}
	
	
	@Test
	public void testLogin() throws ClientProtocolException, IOException
	{
		HttpClient client = HttpClients.createDefault();
		HttpRequestBase request = new HttpGet(JETTY_HOME);
		HttpResponse httpResponse = client.execute( request );
		String response = EntityUtils.toString( httpResponse.getEntity() );
		logger.info("RESPONSE: " + response);
		assertTrue("", httpResponse.getStatusLine().getStatusCode() == 200);
	}
	
	@AfterClass
	public static void shutdown() throws Exception 
	{
		logger.info("Shutting down...");
	}
}
