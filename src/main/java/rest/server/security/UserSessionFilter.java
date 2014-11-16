package rest.server.security;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 
 * HTTP Filter that protects our REST web services and requires a valid HTTP Session in order to access WEB services
 *
 */
public class UserSessionFilter extends OncePerRequestFilter
{	
	public static final String SESSION_USERNAME = "user-name";
	public static final String SESSION_ROLE = "user-role";
	private static Set<Resource> unprotected = new HashSet<Resource>();
	
	private static final void addResources()
	{
		unprotected.add(new Resource("POST", "/user"));
		unprotected.add(new Resource("GET", "/user/validate"));
		unprotected.add(new Resource("POST", "/user/login"));
	}
	
	public UserSessionFilter()
	{
		super();
		UserSessionFilter.addResources();
	}
	
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException
	{	
		HttpSession session = req.getSession();
		
		if (session != null)
		{
			Object user = req.getSession().getAttribute(SESSION_USERNAME);
			if(user == null && this.isProtected(req))
			{
				res.sendRedirect("/user/login.jsp");
			}
		}
		
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
		return !UserSessionFilter.unprotected.contains(new Resource(req.getMethod(), req.getPathInfo()));
	}
}
