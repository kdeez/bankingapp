package rest.server.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rest.server.model.Account;
import rest.server.model.User;

/**
 * Hibernate ORM mplementation of User DAO 
 * @author roger.hagen
 * 
 * @Repository annotation is a flag for Spring to instantiate this class as
 * a Singleton (single instance) when this application starts
 *
 */
@Repository("userDao")
public class HibernateUserDao implements UserDao 
{
	
	/**
	 * @Autowired means that Spring will automatically inject this dependency at runtime
	 * This particular Bean is defined in applicationContext.xml which Spring reads and fires up all beans
	 */
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean authorized(User unverified) 
	{
		User user = this.getUser(unverified.getUsername());
		if(user != null)
		{
			return user.getPassword().equals(unverified.getPassword());
		}
		return false;
	}

	/**
	 * @Transactionl annotation allows Spring to manage the database transaction 
	 * so no need to open session or commit... this is managed by Spring
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public User getUser(long id) {
		return (User) sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Restrictions.eq("id", id))
				.add(Restrictions.eq("active", true)).uniqueResult();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean saveUser(User user) {
		return sessionFactory.getCurrentSession().save(user) != null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public User getUser(String username) {
		return (User) sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Restrictions.eq("username", username))
				.add(Restrictions.eq("active", true)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Account> getAccounts(User user) {
		return sessionFactory.getCurrentSession().createCriteria(Account.class)
				.add(Restrictions.eq("userId", user.getId())).list();
	}

}
