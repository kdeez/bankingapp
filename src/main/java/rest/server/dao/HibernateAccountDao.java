package rest.server.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rest.server.exceptions.TransactionException;
import rest.server.model.Account;
import rest.server.model.Account.Type;
import rest.server.model.Transaction;
import rest.server.model.User;


@Repository("accountDao")
public class HibernateAccountDao implements AccountDao
{
	/**
	 * @Autowired means that Spring will automatically inject this dependency at runtime
	 * This particular Bean is defined in applicationContext.xml which Spring reads and fires up all beans
	 */
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Account getAccount(long accountId) 
	{
		return (Account) sessionFactory.getCurrentSession().createCriteria(Account.class)
				.add(Restrictions.eq("id", accountId)).uniqueResult();
	}

	/**
	 * @Transactionl annotation allows Spring to manage the database transaction 
	 * so no need to open session or commit... this is managed by Spring
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Account getAccount(User user, long id) 
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Account.class).add(Restrictions.eq("id", id));
		if( !(user.getRole().getName().equals("Admin") || user.getRole().getName().equals("Employee")))
		{
			criteria.add(Restrictions.eq("userId", user.getId()));
		}
		return (Account) criteria.uniqueResult();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void saveUpdate(Account account) 
	{
		sessionFactory.getCurrentSession().saveOrUpdate(account);
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
				.addOrder(Order.desc("id"))
				.list();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void saveTransaction(Transaction transaction) 
	{
		sessionFactory.getCurrentSession().save(transaction);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void performTransaction(Account account, Transaction transaction)
			throws TransactionException {
		try 
		{
			Transaction.Type type = Transaction.Type.values()[transaction.getTransactionType()];
			switch(type)
			{
				case DEBIT:
					account.debit(transaction.getAmount());
					break;
				case CREDIT:
					account.credit(transaction.getAmount());
					break;
				default:	
			}
		} catch (TransactionException e) 
		{
			//TODO: RLH, need to return the correct response
			throw e;
		}
		
		transaction.setAccountId(account.getAccountNumber());
		transaction.setBalance(account.getBalance());
		this.saveTransaction(transaction);
		this.saveUpdate(account);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Account> getCustomerAccounts(int firstResult, int maxResults) 
	{
		return sessionFactory.getCurrentSession().createCriteria(Account.class)
				.add(Restrictions.ne("accountType", Type.CAPITOL.ordinal()))
				.setFirstResult(firstResult)
				.setMaxResults(maxResults).list();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteAccount(Account account) 
	{
		sessionFactory.getCurrentSession().delete(account);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public double getMinBalance(Account account, Date from, Date to) 
	{
		List<Transaction> transactions = this.getTransactions(account, from, to);
		if(transactions.isEmpty())
		{
			return account.getBalance();
		}
		
		return (double) sessionFactory.getCurrentSession().createCriteria(Transaction.class)
				.setProjection(Projections.min("balance"))
				.add(Restrictions.eq("accountId", account.getAccountNumber()))
				.add(Restrictions.ge("dateTime", from))
				.add(Restrictions.le("dateTime", to))
				.uniqueResult();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public double getMaxBalance(Account account, Date from, Date to) {
		List<Transaction> transactions = this.getTransactions(account, from, to);
		if(transactions.isEmpty())
		{
			return account.getBalance();
		}
		
		return (double) sessionFactory.getCurrentSession().createCriteria(Transaction.class)
				.setProjection(Projections.max("balance"))
				.add(Restrictions.eq("accountId", account.getAccountNumber()))
				.add(Restrictions.ge("dateTime", from))
				.add(Restrictions.le("dateTime", to))
				.uniqueResult();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void transfer(Account from, Account to, double amount, String description) 
	{
		Transaction debit = new Transaction(from.getAccountNumber(), Transaction.Type.DEBIT.ordinal(), amount, description);
		Transaction credit = new Transaction(to.getAccountNumber(), Transaction.Type.CREDIT.ordinal(), amount, description);
		this.performTransaction(from, debit);
		this.performTransaction(to, credit);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void applyPenalty(Account account, double amount) 
	{
		Account capitolAccount = this.getAccount( (long) 1);
		this.transfer(account, capitolAccount, amount, "Penalty for account: " + account.getAccountNumber());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void applyInterest(Account account, double amount) 
	{
		Account capitolAccount = this.getAccount( (long) 1);
		this.transfer(capitolAccount, account, amount, "Interest for account: " + account.getAccountNumber());
	}



}
