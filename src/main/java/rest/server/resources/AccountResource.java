package rest.server.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;


import rest.server.dao.AccountDao;
import rest.server.model.Account;
import rest.server.dao.UserDao;
import rest.server.model.User;

import java.sql.*;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Properties;

@Controller("AccountResource")
@Path("/account")
public class AccountResource 
{
	
	@Autowired
	@Qualifier("accountDao")
	private AccountDao accountDao;
	
	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response getAccount(@QueryParam("id") Long id){
		Account account = accountDao.getAccount(id);
		return Response.ok(account).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response addAccount(Account account)
	{
		
		/*
		InputStream stream = new FileInputStream("config.properties");
		Properties props = new Properties();
		props.load(stream);

		Class.forName(props.getProperty("db.jdbcdriver", "com.mysql.jdbc.Driver"));
		Connection connection = DriverManager.getConnection(props.getProperty("db.jdbcurl"), props.getProperty("db.userid"), props.getProperty("db.userpwd"));
		
		
		
		//always use PreparedStatements to protect against SQL injection
		PreparedStatement statement = connection.prepareStatement("select * from users where firstName = ? and lastname = ?");
		statement.setString(1, user.getFirstName());
		statement.setString(2, user.getLastname());
		
		ResultSet result = statement.executeQuery();
		if(result == null)
		{
			boolean saved = userDao.saveUser(user);
			if(!saved){
				return Response.serverError().build();
			}*/
			return Response.ok(account).build();
		//}
	}
}
