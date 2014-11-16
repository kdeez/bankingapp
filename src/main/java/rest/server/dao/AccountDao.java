package rest.server.dao;

import java.util.Date;
import java.util.List;

import rest.server.exceptions.TransactionException;
import rest.server.model.Account;
import rest.server.model.Transaction;
import rest.server.model.User;


public interface AccountDao 
{
	
	public Account getAccount(long accountId);
	
	/**
	 * Returns an account if the user has access
	 * @param user
	 * @param id is the account number
	 * @return
	 */
	public Account getAccount(User user, long id);
	
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
	
	
	public List<Account> getCustomerAccounts(int firstResult, int maxResults);
	
	public void deleteAccount(Account account);
	
	public void transfer(Account from, Account to, double amount, String description);
	
	public double getMinBalance(Account account, Date from, Date to);
	
	public void applyPenalty(Account account, double amount);
	
	public void applyInterest(Account account, double amount);
	
}
