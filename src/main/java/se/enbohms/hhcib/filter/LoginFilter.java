package se.enbohms.hhcib.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.enbohms.hhcib.common.Constants;

@WebFilter("/secured/*")
public class LoginFilter implements Filter {

	private static final String LOGIN_URL = "/login/login.xhtml";

	/**
	 * Checks if user is logged in. If not it redirects to the login.xhtml page.
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (noUserInSession(request)) {
			redirectToLoginPage(request, response);
		}
		chain.doFilter(request, response);
	}

	private void redirectToLoginPage(ServletRequest request,
			ServletResponse response) throws IOException {
		String contextPath = ((HttpServletRequest) request).getContextPath();
		String targetUrl = ((HttpServletRequest) request).getRequestURI()
				.substring(contextPath.length());

		((HttpServletRequest) request).getSession(true).setAttribute(
				Constants.TARGET_URL, targetUrl);
		((HttpServletResponse) response).sendRedirect(contextPath + LOGIN_URL);
	}

	private boolean noUserInSession(ServletRequest request) {
		return ((HttpServletRequest) request).getSession().getAttribute(
				Constants.USER) == null;
	}

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}
}