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

import rest.server.dao.UserDao;
import rest.server.model.User;

/**
 * 
 * @author roger.hagen
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
 
}
