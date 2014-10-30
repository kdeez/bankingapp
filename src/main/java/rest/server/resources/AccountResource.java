package rest.server.resources;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import rest.server.model.Transaction;
import rest.server.model.User;

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
	public Response addAccount(@Context HttpServletRequest request, Account account)
	{
		String username = (String) request.getSession().getAttribute(UserSessionFilter.SESSION_USERNAME);
		User user = userDao.getUser(username);
		if(user == null){
			return Response.status(Status.BAD_REQUEST).entity(new String("Invalid user session!")).build();
		}

		account.setUserId(user.getId());
		boolean saved = accountDao.saveAccount(account);
		if(!saved){
			return Response.serverError().build();
		}
		return Response.ok(account).build();
	}
	
	@GET
	@Path("/transactions")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response getTransactions(@Context HttpServletRequest request, @QueryParam("id") long id){
		String username = (String) request.getSession().getAttribute(UserSessionFilter.SESSION_USERNAME);
		User user = userDao.getUser(username);
		if(user == null){
			return Response.status(Status.UNAUTHORIZED).entity(new String("Invalid user session!")).build();
		}
		
		Account account = accountDao.getAccount(id);
		if(account == null || (account.getUserId() != user.getId())){
			return Response.status(Status.UNAUTHORIZED).entity(new String("Unauthorized to access account!")).build();
		}
		
		Calendar calendar = GregorianCalendar.getInstance();
		Date to = calendar.getTime();
		calendar.roll(Calendar.MONTH, -1);
		Date from = calendar.getTime();
		
		List<Transaction> transactions = accountDao.getTransactions(account, from, to);
		return Response.ok(transactions).build();
	}
	
	
	//Will this work for debit? Will it update the account with the new balance? 
	//Was thinking for the path it would be /account/debit/debit.jsp and credit would be /credit/credit.jsp?
	//Was also thinking having buttons on the home page one for credit and one for debit so they dont have to go to another page to pick the account they want to credit/debit
	@POST
	@Path("/debit")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response debit(@Context HttpServletRequest request, @QueryParam("id") long id, @QueryParam("amount") int amount){
		String username = (String) request.getSession().getAttribute(UserSessionFilter.SESSION_USERNAME);
		User user = userDao.getUser(username);
		if(user == null){
			return Response.status(Status.UNAUTHORIZED).entity(new String("Invalid user session!")).build();
		}
		
		Account account = accountDao.getAccount(id);
		if(account == null || (account.getUserId() != user.getId())){
			return Response.status(Status.UNAUTHORIZED).entity(new String("Unauthorized to access account!")).build();
		}
		
		int currentBalance = account.getBalance();
		if(currentBalance == 0)
		{
			//return error account has a balance of zero
			return Response.status(Status.UNAUTHORIZED).entity(new String("Current Balance of Account is $0")).build();
		}
		
		if(amount > currentBalance)
		{
			//return an error insufficient funds
			return Response.status(Status.UNAUTHORIZED).entity(new String("Insufficient funds within the Account")).build();
		}
		
		account.setBalance(currentBalance-amount);
		
		return Response.ok(account).build();
	}
	
}
