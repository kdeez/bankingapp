package rest.server.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rest.server.pojos.User;

@Repository("userDao")
public class HibernateUserDao implements UserDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public User getUser(long id) {
		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean saveUser(User user) {
		return sessionFactory.getCurrentSession().save(user) != null;
	}

}
