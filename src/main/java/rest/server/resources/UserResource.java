package rest.server.resources;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import rest.server.dao.AccountDao;
import rest.server.dao.UserDao;
import rest.server.main.UserSessionFilter;
import rest.server.model.Account;
import rest.server.model.User;

/**
 *
 * This is a web resource that is accessed via HTTP
 * This class is scanned by Spring and JAX-RS on application startup and this is
 * how the web server knows to answer HTTP request and redirect them to this Java class.
 */
@Controller("userResource")
@Path("/user")
public class UserResource {
	
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
	public Response addUser(User user){
		boolean saved = userDao.saveUser(user);
		if(!saved){
			return Response.serverError().build();
		}
		return Response.ok(user).build();
	}
	
	@GET
	@Path("/accounts")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response getAccounts(@Context HttpServletRequest request){
		String username = (String) request.getSession().getAttribute(UserSessionFilter.SESSION_USERNAME);
		User user = userDao.getUser(username);
		if(user == null){
			return Response.status(Status.BAD_REQUEST).entity(new String("Invalid user session!")).build();
		}
		
		List<Account> accounts =  userDao.getAccounts(user);
		return Response.ok(accounts).build();
	}
	
 
}
