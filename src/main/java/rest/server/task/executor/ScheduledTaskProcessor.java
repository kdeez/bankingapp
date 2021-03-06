package rest.server.task.executor;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import rest.server.dao.AccountDao;
import rest.server.model.Account;
import rest.server.task.PenaltyInterestTask;

/**
 * 
 * Bean/Component that is a Thread Pool that can execute any task
 *
 */
@EnableScheduling
@Component("scheduledTaskProcessor")
@SuppressWarnings("serial")
public class ScheduledTaskProcessor extends ThreadPoolTaskExecutor implements TaskProcessor, ApplicationContextAware, SmartLifecycle
{
	private Logger logger = LoggerFactory.getLogger(ScheduledTaskProcessor.class);
	private static ApplicationContext context;
	private boolean started;
	
	@Autowired
	@Qualifier("accountDao")
	private AccountDao accountDao;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public ScheduledTaskProcessor()
	{
		super();
	}
	
	@PostConstruct
	public void init()
	{
		//initialize something if needed
	}

	/**
	 * This method is executed by Spring by a Timer Thread
	 * Executes the first day of every month by way of cron expression
	 */
	@Override
	@Scheduled(cron="0 0 0 1 * ?")
	public void executeTasks() 
	{
		logger.info("Running scheduled tasks...");
		int firstResult = 0, maxResults = 500;
		List<Account> accounts = null;
		//this while loop is paging the results so the server does not go out of memory
		while((accounts = accountDao.getCustomerAccounts(firstResult, maxResults)).isEmpty() == false)
		{
			for(Account account: accounts)
			{
				this.submit(new PenaltyInterestTask(account));
			}
			
			firstResult += maxResults;
		}
		logger.info("Scheduled tasks execution is complete.");
	}

	@Override
	public boolean isRunning() 
	{
		return started;
	}

	@Override
	public void start() 
	{
		if(!started)
		{
			started = true;
			this.setCorePoolSize(1);
			this.setMaxPoolSize(10);
			this.setThreadNamePrefix("task-processor");
			this.setKeepAliveSeconds(300);
			this.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
			this.setWaitForTasksToCompleteOnShutdown(true);
			this.setDaemon(true);
			this.initialize();
		}
		
	}

	@Override
	public void stop() 
	{
		this.shutdown();
		started = false;
		
	}

	@Override
	public int getPhase() 
	{
		return 0;
	}

	@Override
	public boolean isAutoStartup() 
	{
		return false;
	}

	@Override
	public void stop(Runnable callback) 
	{
		stop();
		callback.run();
		
	}
	
	public static ApplicationContext getContext() 
	{
		return context;
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException 
	{
		ScheduledTaskProcessor.context = context;
	}

}
