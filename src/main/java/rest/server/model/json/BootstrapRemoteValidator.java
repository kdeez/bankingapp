package rest.server.model.json;

import java.io.Serializable;

/**
 * 
 * Used for returning responses for Boostrap remote validation
 * 
 * http://bootstrapvalidator.com/validators/remote/
 *
 */
@SuppressWarnings("serial")
public class BootstrapRemoteValidator implements Serializable
{
	private boolean valid;

	public BootstrapRemoteValidator(boolean valid) 
	{
		super();
		this.valid = valid;
	}

	public boolean isValid() 
	{
		return valid;
	}

	public void setValid(boolean valid) 
	{
		this.valid = valid;
	}
	
}

