package se.enbohms.hhcib.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.service.api.UserService;

@Singleton
@Startup
public class UserServiceUtil {

	private List<Email> userEmails = new ArrayList<>();
	private List<String> userNames = new ArrayList<>();

	@Inject
	private UserService userService;
	
	@PostConstruct
	private void init() {
		userEmails.add(Email.of("admin@admin.com"));
		userNames.addAll(userService.getUserNames());
	}

	/**
	 * 
	 * @param email
	 * @return {@code true} if the supplied email exist, {@code false} otherwise
	 */
	public boolean existing(Email email) {
		return userEmails.contains(email);
	}

	/**
	 * 
	 * @param userName
	 * @return {@code true} if the supplied userName is unique, {@code false}
	 *         otherwise
	 */
	public boolean unique(String userName) {
		return !userNames.contains(userName);
	}

	public void setUserEmails(List<Email> userEmails) {
		this.userEmails = userEmails;
	}

	public void setUserNames(List<String> userNames) {
		this.userNames = userNames;
	}
}