package rest.server.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import rest.server.model.Role;
import rest.server.model.User;

/**
 * 
 * HTTP Filter that protects our REST web services and requires a valid HTTP Session in order to access WEB services
 *
 */
public class UserSessionFilter extends OncePerRequestFilter
{	
	private Logger logger = LoggerFactory.getLogger(UserSessionFilter.class);
	private static final List<Role> BANK_EMPLOYEES = Arrays.asList(new Role("Admin"), new Role("Employee"));
	private static Set<Resource> unprotected = new HashSet<Resource>();
	private static Map<Resource, List<Role>> restricted = new HashMap<Resource, List<Role>>();
	public static final String SESSION_USER = "user-name";
	
	private static final void addResources()
	{
		unprotected.add(new Resource("GET", "/user/login.jsp"));
		unprotected.add(new Resource("POST", "/rest/user"));
		unprotected.add(new Resource("GET", "/rest/user/validate"));
		unprotected.add(new Resource("POST", "/rest/user/login"));
		
		restricted.put(new Resource("GET", "/account/deposit.jsp"), BANK_EMPLOYEES);
		restricted.put(new Resource("GET", "/account/debit.jsp"), BANK_EMPLOYEES);
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
		
		String resourcePath = req.getServletPath().equals("/rest") ? req.getPathInfo() : req.getServletPath();
		Resource resource = new Resource(req.getMethod(), resourcePath);
		
		if (session != null)
		{
			Object user = req.getSession().getAttribute(SESSION_USER);
			if(user == null && this.isProtected(resource))
			{
				logger.info("Protected content, redirecting to user login...");
				res.sendRedirect("/user/login.jsp");
			}
			
			if(user != null && this.isRestricted(resource) && !restricted.get(resource).contains(((User)user).getRole()))
			{
				logger.info("Restricted content, redirecting to dashboard...");
				res.sendRedirect("/index.jsp");
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
	private boolean isProtected(Resource resource)
	{
		return !UserSessionFilter.unprotected.contains(resource);
	}
	
	private boolean isRestricted(Resource resource)
	{
		return restricted.containsKey(resource);
	}
}
