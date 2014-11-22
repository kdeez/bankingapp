package rest.server.model;

/**
 * 
 * Interface for a class that has a daily limit of type double
 *
 */
public interface DailyLimitable 
{
	public boolean hasDailyLimit();
	
	public Double getDailyLimit();
}
