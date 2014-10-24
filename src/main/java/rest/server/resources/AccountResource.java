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
import rest.server.dao.UserDao;
import rest.server.model.Account;

@Controller("accountResource")
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
		boolean saved = accountDao.saveAccount(account);
		if(!saved){
			return Response.serverError().build();
		}
		return Response.ok(account).build();
	}
}
