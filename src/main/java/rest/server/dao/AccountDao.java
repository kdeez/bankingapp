package rest.server.dao;

import java.util.Date;
import java.util.List;

import rest.server.model.Account;
import rest.server.model.Transaction;
import rest.server.resources.exceptions.TransactionException;


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
	
	/**
	 * Saves the transaction to the persistent store (database, cache, etc)
	 * @param transaction
	 */
	public void saveTransaction(Transaction transaction);
	
	/**
	 * Performs the business logic on an account when given a transaction
	 * @param account
	 * @param transaction
	 * @throws TransactionException
	 */
	public void performTransaction(Account account, Transaction transaction) throws TransactionException;
	
	
	public List<Account> getAccounts(int firstResult, int maxResults);
	
	
}
