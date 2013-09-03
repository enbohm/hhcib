package se.enbohms.hhcib.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import se.enbohms.hhcib.service.api.LoginService;

public class LoginFilter implements Filter {

	@Inject
	private LoginService loginService;

	/**
	 * Checks if user is logged in. If not it redirects to the login.xhtml page.
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//
		// For the first application request there is no loginBean in the
		// session so user needs to log in
		// For other requests loginBean is present but we need to check if user
		// has logged in successfully
		// if (loginBean == null || !loginBean.isLoggedIn()) {
		//
		// }

		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {

	}

	public void destroy() {

	}

}