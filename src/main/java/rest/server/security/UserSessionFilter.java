package rest.server.security;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 
 * HTTP Filter that protects our REST web services and requires a valid HTTP Session in order to access WEB services
 *
 */
public class UserSessionFilter extends OncePerRequestFilter
{	
	private Logger logger = LoggerFactory.getLogger(UserSessionFilter.class);
	public static final String SESSION_USER = "user-name";
	public static final String SESSION_ROLE = "user-role";
	private static Set<Resource> unprotected = new HashSet<Resource>();
	
	private static final void addResources()
	{
		unprotected.add(new Resource("GET", "/user/login.jsp"));
		
		unprotected.add(new Resource("POST", "/rest/user"));
		unprotected.add(new Resource("GET", "/rest/user/validate"));
		unprotected.add(new Resource("POST", "/rest/user/login"));
	}
	
	public UserSessionFilter()
	{
		super();
		UserSessionFilter.addResources();
	}
	
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException
	{	
		HttpSession session = req.getSession();
		
		String path = (req.getServletPath() != null ? req.getServletPath() : "/") + (req.getPathInfo() != null ? req.getPathInfo() : "");
		logger.info("Intercepted =" + path);
		if (session != null)
		{
			Object user = req.getSession().getAttribute(SESSION_USER);
			if(user == null && this.isProtected(req))
			{
				logger.info("Redirecting to user login...");
				res.sendRedirect("/user/login.jsp");
			}
		}
		else
		{
			logger.info("No valid user session");
		}
		
		logger.info("Authorized =" + path);
		chain.doFilter(req, res);
	}
	
	public static class Resource
	{
		private String method;
		private String path;
		
		public Resource(String method, String path) 
		{
			super();
			this.method = method;
			this.path = path;
		}
		
		@Override
		public int hashCode() 
		{
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((method == null) ? 0 : method.hashCode());
			result = prime * result + ((path == null) ? 0 : path.hashCode());
			return result;
		}
		
		@Override
		public boolean equals(Object obj) 
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Resource other = (Resource) obj;
			if (method == null) {
				if (other.method != null)
					return false;
			} else if (!method.equals(other.method))
				return false;
			if (path == null) {
				if (other.path != null)
					return false;
			} else if (!path.equals(other.path))
				return false;
			return true;
		}
		
	}
	
	/**
	 * Returns true if the resource should be protected by this filter
	 * @param req
	 * @return
	 */
	private boolean isProtected(HttpServletRequest req)
	{
		String resource = req.getServletPath().equals("/rest") ? req.getPathInfo() : req.getServletPath();
		return !UserSessionFilter.unprotected.contains(new Resource(req.getMethod(), resource));
	}
}
