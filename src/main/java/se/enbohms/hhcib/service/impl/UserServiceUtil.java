package se.enbohms.hhcib.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import se.enbohms.hhcib.entity.Email;

@Singleton
@Startup
public class UserServiceUtil {

	private List<Email> userEmails = new ArrayList<>();

	@PostConstruct
	private void init() {
		userEmails.add(Email.of("admin@admin.com"));
	}

	public boolean existing(Email email) {
		return userEmails.contains(email);
	}
	
	public void setUserEmails(List<Email> userEmails) {
		this.userEmails = userEmails;
	}
}
