package rest.server.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import rest.server.dao.AccountDao;
import rest.server.model.Account;
import rest.server.task.executor.ScheduledTaskProcessor;

public class CompoundInterestTask implements RunnableTask, ApplicationContextAware
{
	private Logger logger = LoggerFactory.getLogger(ScheduledTaskProcessor.class);
	private AccountDao accountDao;
	private Account account;
	
	
	public CompoundInterestTask(Account account) 
	{
		super();
		this.account = account;
	}

	@Override
	public Result call() throws Exception 
	{
		//TODO:1. need to calculate interest and penalties
		//TODO:2. save a transaction that has "Interest" or "Penalty" as the description
		//TODO:3. update the account balance
		logger.info("Running task for account=" + account);
		return Result.SUCCESS;
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException 
	{
		this.accountDao = context.getBean(AccountDao.class);
	}


}
