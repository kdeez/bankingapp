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
import rest.server.model.Transaction.Type;
import rest.server.model.json.BootstrapRemoteValidator;
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
		if(user == null)
		{
			return Response.status(Status.BAD_REQUEST).entity(new String("Invalid user session!")).build();
		}

		account.setUserId(user.getId());
		accountDao.saveUpdate(account);
		return Response.ok(account).build();
	}
	
	@GET
	@Path("/validate")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response accountExists(@QueryParam("accountId") long accountId)
	{
		return Response.ok(new BootstrapRemoteValidator(accountDao.getAccount(accountId) != null)).build();
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
	
	@POST
	@Path("/transactions")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response executeTransaction(@Context HttpServletRequest request, Transaction transaction)
	{
		String username = (String) request.getSession().getAttribute(UserSessionFilter.SESSION_USERNAME);
		User user = userDao.getUser(username);
		if(user == null)
		{
			return Response.status(Status.UNAUTHORIZED).entity(new String("Invalid user session!")).build();
		}
		
		Account account = accountDao.getAccount(transaction.getAccountId());
		if(account == null || (account.getUserId() != user.getId()))
		{
			return Response.status(Status.UNAUTHORIZED).entity(new String("Unauthorized to access account!")).build();
		}
		
		accountDao.performTransaction(account, transaction);
		
		return Response.ok(transaction).build();
	}

	
	@POST
	@Path("/transfer")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response transfer(@Context HttpServletRequest request, @QueryParam("id") long id, Transaction credit)
	{
		if(credit.getTransactionType() != Type.CREDIT.ordinal())
		{
			return Response.status(Status.UNAUTHORIZED).entity(new String("Unauthorized to execute transaction!")).build();
		}
		
		String username = (String) request.getSession().getAttribute(UserSessionFilter.SESSION_USERNAME);
		User user = userDao.getUser(username);
		if(user == null)
		{
			return Response.status(Status.UNAUTHORIZED).entity(new String("Invalid user session!")).build();
		}
		
		Account from = accountDao.getAccount(id);
		if(from == null || (from.getUserId() != user.getId()))
		{
			return Response.status(Status.UNAUTHORIZED).entity(new String("Unauthorized to access account!")).build();
		}
		
		Account to = accountDao.getAccount(credit.getAccountId());
		if(to == null)
		{
			return Response.status(Status.UNAUTHORIZED).entity(new String("Unauthorized to access account!")).build();
		}
		
		Transaction debit = new Transaction(id, Type.DEBIT.ordinal(), credit.getAmount(), "Transfer to account: " + credit.getAccountId());
		accountDao.performTransaction(from, debit);
		accountDao.performTransaction(to, credit);
		
		return Response.ok(credit).build();
	}
	
}
