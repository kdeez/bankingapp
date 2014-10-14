package rest.server.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.http.MediaType;

import rest.server.pojos.User;

@Path("/user")
public class UserResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response getUser(@QueryParam("id") String id){
		//this is temporary for demo...
		//TODO: implement hibernate DAO
		User user = new User();
		user.setFirstName("Roger Hagen");
		user.setLastname("Hagen");
		return Response.ok(user).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response addUser(User user){
		return null;
	}
	 
}
