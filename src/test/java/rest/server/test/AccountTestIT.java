package rest.server.test;

import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.sql.DataSource;
import javax.ws.rs.core.Response.Status;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import rest.server.dao.AccountDao;
import rest.server.dao.UserDao;
import rest.server.model.Account;
import rest.server.model.User;
import rest.server.model.json.BootstrapRemoteValidator;
import rest.server.utils.JSON;

/**
 * 
 * This test uses Hibernate H2 database and not MySQL. 
 * The database is created dynamically at runtime by scanning the JPA annotations.
 *
 */
public class AccountTestIT { 
	
	private static final Logger logger = LoggerFactory.getLogger(AccountTestIT.class);
	private final String ACCOUNT_DESCRIPTION = "flerp-derp";
	private final String ACCOUNT_REQUEST     = "http://localhost:8081/rest/account";
	private final String DELETE_ACCOUNT      = "http://localhost:8081/rest/account?id=";
	private final String LOGIN_REQUEST       = "http://localhost:8081/rest/user/login";
	private final String TRANSACTION_REQUEST = "http://localhost:8081/rest/account/transactions";
	
	private static DataSource     dataSource;
	private static SessionFactory sessionFactory;
	private static UserDao        userDao;
	private static AccountDao     accDao;
	
	@BeforeClass
	public static void sysProp() throws Exception 
	{
		//starts the web server using properties that were created for testing
		String properties = System.getProperty("user.dir") + "/src/test/resources/config.properties";
		rest.server.main.JettyServer.main(new String[]{properties});
		
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		dataSource = (DataSource) context.getBean("dataSource");
		sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		userDao = context.getBean(UserDao.class);
		accDao  = context.getBean(AccountDao.class);
		
		InputStream is = AccountTestIT.class.getResourceAsStream("/db.sql");
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			ScriptRunner sr = new ScriptRunner(conn);
			sr.setStopOnError(true);
			sr.setAutoCommit(false);
			sr.setLogWriter(null);
			sr.setErrorLogWriter(new PrintWriter(System.out));
			sr.runScript(new InputStreamReader(is));
		} catch (Exception ex) {
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Throwable th) {
				}
			}
		}
	}
	
//	@Test
	public void transferFundsBetweenUsers() throws Exception {
		
		HttpClient client = HttpClients.createDefault();
		HttpClientContext context = HttpClientContext.create();
		
		// login admin
		loginUser(new User("admin","password"),client,context);
		
		// add account1 & deposit $30
		Account account1 = addAccount("test description", "0", client, context);
		account1 = depositToAccount(account1, 30.00, client);
		
		// add account2 & deposit $30
		Account account2 = addAccount("test description", "0", client, context);
		account2 = depositToAccount(account2, 30.00, client);
		
		// might need to use same client but this seems to work too...
		HttpClient client2 = HttpClients.createDefault();
		HttpClientContext context2 = HttpClientContext.create();
		
		// transfer funds
		HttpPost request = new HttpPost("http://localhost:8081/rest/account/transfer?id="+account2.getAccountNumber());
		request.setHeader("Content-Type", "application/json");
		
		// TODO : play with the string entity object - this is what tells the request what it is asking the servlet to do
//		String entity = "{\"accountId\":\""+account2.getAccountNumber()+"\",\"transactionType\":\"1\",\"amount\":\"5.00\",\"description\":\"test transfer\"}";
		String entity = "{\"transactionType\":\"1\",\"accountId\":\"" + account1.getAccountNumber() + "\",\"description\":\"dinner\",\"amount\":\"5.00\"}";
		
		// set request entity
		request.setEntity(new StringEntity(entity));
		HttpResponse response = client2.execute(request, context2);
		
		// TODO: get this assertion to pass
//		assertTrue("Should have recieved a HTTP 200 response", response.getStatusLine().getStatusCode() == Status.OK.getStatusCode());
		
		logger.info("RESPONSE: "+response.getStatusLine().getStatusCode());
		
		account1 = accDao.getAccount(account1.getAccountNumber());
		account2 = accDao.getAccount(account2.getAccountNumber());

		// output - final account balances, the transfer should have occured
		logger.info("TRANSFER ACCOUNT1: "+account1.getBalance());
		logger.info("TRANSFER ACCOUNT2: "+account2.getBalance());
		
		// TODO: turn the above output statements into assertions which appropriately pass
	}
	
	@Test
	public void createAccount() throws Exception {
		HttpClient client = HttpClients.createDefault();
		HttpClientContext context = HttpClientContext.create();
		
		// login user
		loginUser(new User("admin","password"),client,context);
		
		// create new account
		HttpPost accountRequest = new HttpPost("http://localhost:8081/rest/account");
		accountRequest.setHeader("Content-Type", "application/json");
		accountRequest.setEntity(new StringEntity("{\"description\":\""+ACCOUNT_DESCRIPTION+"\",\"accountType\":\"0\"}"));
		HttpResponse response2 = client.execute(accountRequest);
		
		// test account persisted ok
		assertTrue("Should have recieved a HTTP 200 response", response2.getStatusLine().getStatusCode() == Status.OK.getStatusCode());
		Account account = (Account) JSON.deserialize(EntityUtils.toString( response2.getEntity() ), Account.class);
		assertTrue("Description should be"+ACCOUNT_DESCRIPTION, ACCOUNT_DESCRIPTION.equals(account.getDescription()));
	}
	
	@Test
	public void closeAccount() throws Exception {
		HttpClient client = HttpClients.createDefault();
		HttpClientContext context = HttpClientContext.create();
		
		// login user
		loginUser(new User("admin","password"),client,context);
		
		// add account
		Account account = addAccount("description", "0", client, context);
		assertTrue("description".equals(account.getDescription()));
		assertTrue("Account should be active", account.isActive());
		
		// delete account please:)
		HttpDelete request = new HttpDelete(DELETE_ACCOUNT+account.getAccountNumber());
		HttpResponse response = client.execute(request);
		
		assertTrue("Should have recieved a HTTP 200 response", response.getStatusLine().getStatusCode() == Status.OK.getStatusCode());
		User user = userDao.getUser("admin");
		account = accDao.getAccount(user, account.getAccountNumber());
		assertTrue("Account should not be active", !account.isActive());
	}
	
	@Test
	public void depositAccount() throws Exception {
		HttpClient client = HttpClients.createDefault();
		HttpClientContext context = HttpClientContext.create();
		
		// login user
		loginUser(new User("admin","password"),client,context);
		
		// add account
		Account account = addAccount("test description", "0", client, context);
		
		// configure request
		HttpPost request = new HttpPost(TRANSACTION_REQUEST);
		request.setHeader("Content-Type", "application/json");
		String entity = "{\"accountId\":\""+account.getAccountNumber()+"\",\"transactionType\":\"1\",\"amount\":\"30.00\",\"description\":\"test deposit\"}";
		request.setEntity(new StringEntity(entity));
		
		// execute request noting balance
		double startingBalance = account.getBalance();
		HttpResponse response = client.execute(request);
		
		assertTrue("Should have recieved a HTTP 200 response", response.getStatusLine().getStatusCode() == Status.OK.getStatusCode());
		account = accDao.getAccount(account.getAccountNumber());
		double endingBalance = account.getBalance();
		assertTrue("30 dollars should have been added.", endingBalance - startingBalance == 30.0);
	}
	
	@Test
	public void debitAccount() throws Exception {
		HttpClient client = HttpClients.createDefault();
		HttpClientContext context = HttpClientContext.create();
		
		// login user
		loginUser(new User("admin","password"),client,context);
		
		// add account
		Account account = addAccount("test description", "0", client, context);
		
		// add funds
		account = depositToAccount(account, 30.00, client);
		
		// configure debit request
		HttpPost request = new HttpPost(TRANSACTION_REQUEST);
		request.setHeader("Content-Type", "application/json");
		String entity = "{\"accountId\":\""+account.getAccountNumber()+"\",\"transactionType\":\"0\",\"amount\":\"5.00\",\"description\":\"test debit\"}";
		request.setEntity(new StringEntity(entity));
		
		// execute request noting balance
		double startingBalance = account.getBalance();
		HttpResponse response = client.execute(request);
		
		assertTrue("Should have recieved a HTTP 200 response", response.getStatusLine().getStatusCode() == Status.OK.getStatusCode());
		account = accDao.getAccount(account.getAccountNumber());
		double endingBalance = (int)account.getBalance();
		assertTrue("5 dollars should have been debited.", startingBalance - endingBalance == 5.0);
	}
	
	public Account depositToAccount(Account account, double amount, HttpClient client) throws Exception {
		HttpPost request = new HttpPost(TRANSACTION_REQUEST);
		request.setHeader("Content-Type", "application/json");
		String entity = "{\"accountId\":\""+account.getAccountNumber()+"\",\"transactionType\":\"1\",\"amount\":\"30.00\",\"description\":\"test deposit\"}";
		request.setEntity(new StringEntity(entity));
		
		// execute request noting balance
		double startingBalance = account.getBalance();
		HttpResponse response = client.execute(request);
		
		assertTrue("Should have recieved a HTTP 200 response", response.getStatusLine().getStatusCode() == Status.OK.getStatusCode());
		account = accDao.getAccount(account.getAccountNumber());
		double endingBalance = account.getBalance();
		assertTrue("30 doll hairs should have been added.", endingBalance - startingBalance == 30.0);
		
		return account;
	}
	
	/**
	 * This method will add an account to the logged in user
	 * @param client
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public Account addAccount(String description, String type, HttpClient client, HttpClientContext context) throws Exception {
		
		// create new account
		HttpPost accountRequest = new HttpPost(ACCOUNT_REQUEST);
		accountRequest.setHeader("Content-Type", "application/json");
		accountRequest.setEntity(new StringEntity("{\"description\":\"" + description + "\",\"accountType\":\""+ type +"\"}"));
		HttpResponse response2 = client.execute(accountRequest, context);
		
		// test account persisted ok
		assertTrue("Should have recieved a HTTP 200 response", response2.getStatusLine().getStatusCode() == Status.OK.getStatusCode());
		Account account = (Account) JSON.deserialize(EntityUtils.toString( response2.getEntity() ), Account.class);
		assertTrue("Description should be"+description, description.equals(account.getDescription()));
		
		return account;
	}
	
	/**
	 * This method will log in the user passed in and test that
	 * everything went ok.
	 * @param user
	 * @param client
	 * @param context
	 * @throws Exception
	 */
	public void loginUser(User user, HttpClient client, HttpClientContext context) throws Exception {
		
		// login user
		HttpPost loginRequest = new HttpPost(LOGIN_REQUEST);
		loginRequest.setHeader("Content-Type", "application/json");
		String json = JSON.serialize(user);
		StringEntity body = new StringEntity(json);
		loginRequest.setEntity(body);
		HttpResponse response1 = client.execute(loginRequest, context);
		
		// test login was good
		BootstrapRemoteValidator validator = (BootstrapRemoteValidator) JSON.deserialize(EntityUtils.toString( response1.getEntity()), BootstrapRemoteValidator.class);
		assertTrue("The user should have been authenticated", validator.isValid());
		assertTrue("Should have recieved a HTTP 200 response", response1.getStatusLine().getStatusCode() == Status.OK.getStatusCode());
	}
	
	@AfterClass
	public static void shutdown() throws Exception 
	{
		logger.info("Shutting down...");
	}
}