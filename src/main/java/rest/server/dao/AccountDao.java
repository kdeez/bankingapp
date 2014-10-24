package rest.server.dao;

import rest.server.model.Account;


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
	
}
