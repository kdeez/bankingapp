package rest.server.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rest.server.model.Account;
import rest.server.model.Role;
import rest.server.model.User;
import rest.server.security.KeyAuthenticator;

/**
 * Hibernate ORM mplementation of User DAO 
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
		
		if (user != null)
		{
			return (KeyAuthenticator.verifyKey(unverified.getPassword(), user.getPassword()));
		}
		else
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
	public boolean save(User user) {
		return sessionFactory.getCurrentSession().save(user) != null;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void update(User user) 
	{
		sessionFactory.getCurrentSession().update(user);
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
	public User getUserByEmail(String email) {
		List<User> users = sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Restrictions.eq("email", email)).list();
		
		//unique so its valid
		if(users.size() == 1)
		{
			return users.get(0);
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public User getUserByPhone(String phone) {
		List<User> users = sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Restrictions.eq("phone", phone)).list();
		
		//unique so its valid
		if(users.size() == 1)
		{
			return users.get(0);
		}
		
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Account getDefaultAccount(User user) {
		return (Account) sessionFactory.getCurrentSession().createCriteria(Account.class)
				.add(Restrictions.eq("userId", user.getId()))
				.add(Restrictions.eq("active", true))
				.addOrder(Order.asc("id"))
				.setMaxResults(1)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Account> getActiveAccounts(User user) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Account.class)
				.add(Restrictions.eq("active", true));
		
		if( !(user.getRole().getName().equals("Admin") || user.getRole().getName().equals("Employee")))
		{
			criteria.add(Restrictions.eq("userId", user.getId()));
		}
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Account> getInActiveAccounts(User user) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Account.class)
				.add(Restrictions.eq("active", false));
		
		if( !(user.getRole().getName().equals("Admin") || user.getRole().getName().equals("Employee")))
		{
			criteria.add(Restrictions.eq("userId", user.getId()));
		}
		
		return criteria.list();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Role getRole(String name) {
		return (Role) sessionFactory.getCurrentSession().createCriteria(Role.class).add(Restrictions.eq("name", name)).uniqueResult();
	}

}
