package se.enbohms.hhcib.facade;

import java.io.Serializable;

import javax.ejb.Singleton;
import javax.inject.Named;

import se.enbohms.hhcib.entity.Category;

@Singleton
@Named
public class CategoryFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private String categoryValue;

	public String getCategoryValue() {
		return categoryValue;
	}

	public void setCategoryValue(String categoryValue) {
		this.categoryValue = categoryValue;
	}

	public Category[] getCategories() {
		return Category.values();
	}
}