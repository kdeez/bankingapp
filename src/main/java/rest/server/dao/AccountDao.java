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
	public void saveUpdate(Account account);
	
	/**
	 * Returns a list of Transactions that occurred for the Account between the two Dates
	 * @param account
	 * @param from
	 * @param to
	 * @return
	 */
	public List<Transaction> getTransactions(Account account, Date from, Date to);
	
	
	public void saveTransaction(Transaction transaction);
	
	
}
