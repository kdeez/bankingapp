package rest.server.model;

public interface DailyLimitable 
{
	public boolean hasDailyLimit();
	
	public Double getDailyLimit();
}
