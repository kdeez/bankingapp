package rest.server.main;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 
 * @author roger.hagen
 * 
 * HTTP Filter that protects our REST web services and requires a valid HTTP Session in order to access WEB services
 *
 */
public class UserSessionFilter extends OncePerRequestFilter
{	
	public static final String SESSION_USERNAME = "user-name";
	
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException
	{	
		HttpSession session = req.getSession();
		
		if (session != null)
		{
			Object user = req.getSession().getAttribute(SESSION_USERNAME);
			if(user == null)
			{
				res.sendRedirect("/login.jsp");
			}
		}
		
		chain.doFilter(req, res);
	}
}