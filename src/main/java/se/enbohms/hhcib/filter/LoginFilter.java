package se.enbohms.hhcib.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.enbohms.hhcib.service.api.LoginService;

@WebFilter("/secured/*")
public class LoginFilter implements Filter {

	@Inject
	private LoginService loginService;

	/**
	 * Checks if user is logged in. If not it redirects to the login.xhtml page.
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (((HttpServletRequest) request).getSession().getAttribute("user") == null) {
			String contextPath = ((HttpServletRequest) request)
					.getContextPath();
			((HttpServletResponse) response).sendRedirect(contextPath
					+ "/login.xhtml");
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {

	}

	public void destroy() {

	}

}