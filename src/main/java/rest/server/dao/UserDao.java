package rest.server.dao;

import rest.server.pojos.User;

public interface UserDao {
	
	public User getUser(long id);
	
	public boolean saveUser(User user);

}
