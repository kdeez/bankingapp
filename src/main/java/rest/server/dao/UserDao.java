package rest.server.dao;

import java.util.List;

import rest.server.model.Account;
import rest.server.model.Role;
import rest.server.model.User;

/**
 * Data Access Object for User model
 * This is an interface so that the implementing class can be 
 * later changed without breaking the rest of the application
 * @author roger.hagen
 *
 */
public interface UserDao 
{
	/**
	 * Returns true if the user is authorized
	 * @param unverified
	 * @return
	 */
	public boolean authorized(User unverified);
	/**
	 * Returns a User
	 * @param id is the unique identifier for the user i.e. primary key of database
	 * @return
	 */
	public User getUser(long id);
	
	/**
	 * Persists (stores) the User to the databases
	 * @param user
	 * @return
	 */
	public boolean saveUser(User user);
	
	
	/**
	 * Look up the User by username
	 * @param username
	 * @return
	 */
	public User getUser(String username);
	
	
	/**
	 * Returns all active accounts that the user has access to
	 * @param user
	 * @return
	 */
	public List<Account> getActiveAccounts(User user);
	
	/**
	 * Returns all inactive accounts that the user has access to
	 * @param user
	 * @return
	 */
	public List<Account> getInActiveAccounts(User user);
	
	/**
	 * Returns the role by name
	 * @param name
	 * @return
	 */
	public Role getRole(String name);

}
