package rest.server.dao;

import rest.server.model.User;

/**
 * Data Access Object for User model
 * This is an interface so that the implementing class can be 
 * later changed without breaking the rest of the application
 * @author roger.hagen
 *
 */
public interface UserDao {
	
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

}
