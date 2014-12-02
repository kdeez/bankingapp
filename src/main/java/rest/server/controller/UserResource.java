package rest.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import rest.server.dao.AccountDao;
import rest.server.dao.UserDao;
import rest.server.model.Account;
import rest.server.model.User;
import rest.server.model.json.BootstrapRemoteValidator;
import rest.server.security.KeyAuthenticator;
import rest.server.security.UserSessionFilter;

/**
 *
 * This is a web resource that is accessed via HTTP
 * This class is scanned by Spring and JAX-RS on application startup and this is
 * how the web server knows to answer HTTP request and redirect them to this Java class.
 */
@Controller("userResource")
@Path("/user")
public class UserResource 
{
	private Logger logger = LoggerFactory.getLogger(UserResource.class);
	
	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;
	
	@Autowired
	@Qualifier("accountDao")
	private AccountDao accountDao;

	@GET
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response getUser(@QueryParam("id") Long id){
		User user = userDao.getUser(id);
		return Response.ok(user).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response addUser(User user)
	{
		user.setActive(true);
		user.setDeletable(true);
		user.setRole(userDao.getRole("Customer"));
		user.setPassword(KeyAuthenticator.getHashCode(user.getPassword()));
		
		boolean saved = userDao.save(user);
		if(!saved){
			return Response.serverError().build();
		}
		return Response.ok(user).build();
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response login(@Context HttpServletRequest request, User unverified)
	{
		boolean authorized = userDao.authorized(unverified);
		if(authorized)
		{
			//create a HTTPSession to use for this session
			User user = userDao.getUser(unverified.getUsername());
			request.getSession().setAttribute(UserSessionFilter.SESSION_USER, user);
			logger.info("Created user session for user=" + user);
		}
		else
		{
			logger.error("Invalid login attempt for user=" + unverified);	
		}
		
		return Response.ok(new BootstrapRemoteValidator(authorized)).build();
	}
	
	@GET
	@Path("/validate")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response isUserAvailable(@QueryParam("username") String username)
	{
		User user = userDao.getUser(username);
		return Response.ok(new BootstrapRemoteValidator(user == null)).build();
	}
	
	@GET
	@Path("/accounts")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response getAccounts(@Context HttpServletRequest request){
		User user = (User) request.getSession().getAttribute(UserSessionFilter.SESSION_USER);
		if(user == null){
			return Response.status(Status.BAD_REQUEST).entity(new String("Invalid user session!")).build();
		}
		
		List<Account> accounts =  userDao.getActiveAccounts(user);
		return Response.ok(accounts).build();
	}
	
	@GET
	@Path("/inactiveAccounts")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response getInActiveAccounts(@Context HttpServletRequest request){
		User user = (User) request.getSession().getAttribute(UserSessionFilter.SESSION_USER);
		if(user == null){
			return Response.status(Status.BAD_REQUEST).entity(new String("Invalid user session!")).build();
		}
		List<Account> accounts =  userDao.getInActiveAccounts(user);
		return Response.ok(accounts).build();
	}
}
