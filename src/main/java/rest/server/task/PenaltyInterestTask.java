package rest.server.task;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import rest.server.dao.AccountDao;
import rest.server.model.Account;
import rest.server.task.executor.ScheduledTaskProcessor;

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
	public Result call() throws Exception 
	{
		Calendar calendar = GregorianCalendar.getInstance();
		Date to = calendar.getTime();
		calendar.roll(Calendar.MONTH, -1);
		Date from = calendar.getTime();
		
		double minBalance = accountDao.getMinBalance(account, from, to);
		
		//TODO:1. need to calculate interest and penalties
		//TODO:2. save a transaction that has "Interest" or "Penalty" as the description
		//TODO:3. update the account balance
		logger.info("Running task for account=" + account);
		return Result.SUCCESS;
	}


}
