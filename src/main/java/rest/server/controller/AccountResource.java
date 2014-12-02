package rest.server.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import rest.server.exceptions.TransactionException;
import rest.server.model.Account;
import rest.server.model.Transaction;
import rest.server.model.Transaction.Type;
import rest.server.model.User;
import rest.server.model.json.BootstrapRemoteValidator;
import rest.server.security.UserSessionFilter;

/**
 * 
 * Controller for Account transactions and business logic
 *
 */
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
	public Response getAccount(@Context HttpServletRequest request, @QueryParam("id") Long id)
	{
		User user = (User) request.getSession().getAttribute(UserSessionFilter.SESSION_USER);
		Account account = accountDao.getAccount(user, id);
		return Response.ok(account).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response addAccount(@Context HttpServletRequest request, Account account)
	{
		User user = (User) request.getSession().getAttribute(UserSessionFilter.SESSION_USER);
		account.setUserId(user.getId());
		account.setActive(true);
		
		switch(Account.Type.values()[account.getAccountType()])
		{
		case CHECKING:
			account.setDailyLimit(10000d);
			break;
		case SAVINGS:
			account.setDailyLimit(5000d);
			break;
		default:
			account.setDailyLimit(null);
		}
		
		accountDao.saveUpdate(account);
		return Response.ok(account).build();
	}
	
	@DELETE
	public Response closeAccount(@Context HttpServletRequest request, @QueryParam("id") Long id)
	{
		User user = (User) request.getSession().getAttribute(UserSessionFilter.SESSION_USER);
		Account account = accountDao.getAccount(user, id);
		
		if (account.getBalance() != 0) {
			return Response.status(Status.BAD_REQUEST).entity(new String("Account needs to be empty!")).build();
		}
		
		try 
		{
			accountDao.close(account);
		}
		catch (Exception ex) 
		{
			return Response.status(Status.BAD_REQUEST).entity(new String("Account cannot be deleted!")).build();
		}
		
		boolean active = ! userDao.getActiveAccounts(user).isEmpty();
		if( !active)
		{
			//disables the user account if no active accounts exist
			user.setActive(active);
			userDao.update(user);
			
			//invalidate the session
			request.getSession().removeAttribute(UserSessionFilter.SESSION_USER);
			request.getSession().invalidate();
		}
		
		return Response.ok().build();
	}
	
	@POST
	@Path("/reactivate")
	public Response reactivateAccount(@Context HttpServletRequest request, @QueryParam("id") Long id)
	{
		User user = (User) request.getSession().getAttribute(UserSessionFilter.SESSION_USER);
		Account account = accountDao.getAccount(user, id);
		
		try {
			accountDao.reactivate(account);
		}
		catch (Exception ex) 
		{
			return Response.status(Status.BAD_REQUEST).entity(new String("Account cannot be reactivated!")).build();
		}
		return Response.ok().build();
	}
	
	@GET
	@Path("/validate")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response isAccountActive(@QueryParam("accountId") long accountId)
	{
		Account account = accountDao.getAccount(accountId);
		return Response.ok(new BootstrapRemoteValidator(account != null && account.isActive())).build();
	}
	
	@GET
	@Path("/transactions")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response getTransactions(@Context HttpServletRequest request, @QueryParam("id") long id){
		User user = (User) request.getSession().getAttribute(UserSessionFilter.SESSION_USER);
		
		Account account = accountDao.getAccount(user, id);
		if(account == null)
		{
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
		User user = (User) request.getSession().getAttribute(UserSessionFilter.SESSION_USER);
		
		Account account = accountDao.getAccount(user, transaction.getAccountId());
		if(account == null || !account.isActive())
		{
			return Response.status(Status.UNAUTHORIZED).entity(new String("Unauthorized to access account!")).build();
		}
		
		if(!(user.getRole().getName().equals("Admin") || user.getRole().getName().equals("Employee")) && transaction.getTransactionType() == Transaction.Type.CREDIT.ordinal())
		{
			return Response.status(Status.UNAUTHORIZED).entity(new String("Unauthorized to complete transaction!")).build();
		}
		
		//verify the daily limit
		if(account.hasDailyLimit())
		{
			Calendar calendar = GregorianCalendar.getInstance();
			Date to = calendar.getTime();
			
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date from = calendar.getTime();
			
			List<Transaction> transactions = accountDao.getTransactions(account, from, to);
			double total = 0;
			for(Transaction tranny: transactions)
			{
				total += tranny.getAmount();
			}
			
			if((total + transaction.getAmount()) > account.getDailyLimit())
			{
				return Response.status(Status.UNAUTHORIZED).entity(new String("Unauthorized to complete transaction, daily limit exceeded!")).build();
			}
			
		}
		
		try 
		{
			accountDao.performTransaction(account, transaction);
		} catch (TransactionException e) 
		{
			if(e instanceof TransactionException)
			{
				return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
			}
			
			return Response.status(Status.BAD_REQUEST).entity("Bad Request").build();
		}
		
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
		
		User user = (User) request.getSession().getAttribute(UserSessionFilter.SESSION_USER);
		
		Account from = accountDao.getAccount(user, id);
		if(from == null || !from.isActive())
		{
			return Response.status(Status.UNAUTHORIZED).entity(new String("Unauthorized to access account!")).build();
		}
		
		Account to = accountDao.getAccount(credit.getAccountId());
		if(to == null || !to.isActive())
		{
			return Response.status(Status.UNAUTHORIZED).entity(new String("Unauthorized to access account!")).build();
		}
		
		if(from.getAccountNumber() == to.getAccountNumber())
		{
			return Response.status(Status.UNAUTHORIZED).entity(new String("Cannot transfer funds to same account")).build();
		}
		
		double amount = credit.getAmount();
		try 
		{
			accountDao.transfer(from, to, amount, "Transfer to account: " + credit.getAccountId());
		} catch (Exception e) 
		{
			if(e instanceof TransactionException)
			{
				return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
			}
			
			return Response.status(Status.BAD_REQUEST).entity("Bad Request").build();
		}
		
		return Response.ok(credit).build();
	}
	
}
