package rest.server.dao;

import java.util.Date;
import java.util.List;

import rest.server.model.Account;
import rest.server.model.Transaction;


public interface AccountDao 
{
	/**
	 * Returns a Account
	 * @param id is the unique identifier for the user i.e. primary key of database
	 * @return
	 */
	public Account getAccount(long id);
	
	/**
	 * Persists (stores) the Account to the databases
	 * @param account
	 * @return
	 */
	public boolean saveAccount(Account account);
	
	/**
	 * 
	 * @param account
	 * @param from
	 * @param to
	 * @return
	 */
	public List<Transaction> getTransactions(Account account, Date from, Date to);
	
}
