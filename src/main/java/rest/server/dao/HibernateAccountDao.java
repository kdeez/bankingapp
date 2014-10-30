package rest.server.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rest.server.model.Account;
import rest.server.model.Transaction;

/* author: Bradley Furman, Kevin Dang, Roger*/

@Repository("accountDao")
public class HibernateAccountDao implements AccountDao
{
	/**
	 * @Autowired means that Spring will automatically inject this dependency at runtime
	 * This particular Bean is defined in applicationContext.xml which Spring reads and fires up all beans
	 */
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * @Transactionl annotation allows Spring to manage the database transaction 
	 * so no need to open session or commit... this is managed by Spring
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Account getAccount(long id) 
	{
		return (Account) sessionFactory.getCurrentSession().createCriteria(Account.class).add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean saveAccount(Account account) 
	{
		return sessionFactory.getCurrentSession().save(account) != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Transaction> getTransactions(Account account, Date from, Date to) 
	{
		return sessionFactory.getCurrentSession().createCriteria(Transaction.class)
				.add(Restrictions.eq("accountId", account.getAccountNumber()))
				.add(Restrictions.ge("dateTime", from))
				.add(Restrictions.le("dateTime", to))
				.list();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void saveTransaction(Transaction transaction) 
	{
		sessionFactory.getCurrentSession().save(transaction);
	}
	
}
