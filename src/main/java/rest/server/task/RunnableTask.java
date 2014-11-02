package rest.server.task;

import java.util.concurrent.Callable;

import rest.server.task.RunnableTask.Result;

public interface RunnableTask extends Callable<Result>
{
	public enum Result
	{
		SUCCESS,
		FAILED
	}
}
