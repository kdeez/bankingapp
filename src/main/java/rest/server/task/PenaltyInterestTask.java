package rest.server.task;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rest.server.dao.AccountDao;
import rest.server.model.Account;
import rest.server.task.executor.ScheduledTaskProcessor;

/**
 * 
 * A Callable (Thread) that can be ran to calculate penalty and interest for an account
 *
 */
public class PenaltyInterestTask implements RunnableTask
{
	private Logger logger = LoggerFactory.getLogger(ScheduledTaskProcessor.class);
	private AccountDao accountDao;
	private Account account;
	
	
	public PenaltyInterestTask(Account account) 
	{
		super();
		this.account = account;
		this.accountDao = ScheduledTaskProcessor.getContext().getBean(AccountDao.class);
	}
	
	@Override
	public Result call()
	{
		try 
		{
			Calendar calendar = GregorianCalendar.getInstance();
			Date to = calendar.getTime();
			calendar.roll(Calendar.MONTH, -1);
			Date from = calendar.getTime();
			
			double maxBalance = accountDao.getMaxBalance(account, from, to);
			if(maxBalance < 100)
			{
				accountDao.applyPenalty(account, 25);
				logger.info("Applied penalty to " + account);
			}
			
			if(account.getType() == Account.Type.SAVINGS)
			{
				double amount = 0;
				double minBalance = accountDao.getMinBalance(account, from, to);
				if(minBalance > 3000)
				{
					amount = minBalance * 0.04;
				}
				else
				if(minBalance > 2000)
				{
					amount = minBalance * 0.03;
				}
				else
				if(minBalance > 1000)
				{
					amount = minBalance * 0.02;
				}
				
				if(amount > 0)
				{
					accountDao.applyInterest(account, amount);
				}
			}
		} catch (Exception e) 
		{
			logger.warn("task failed for account=" + account, e);
			return Result.FAILED;
		}
		
		logger.info("Running task for account=" + account);
		return Result.SUCCESS;
	}


}
