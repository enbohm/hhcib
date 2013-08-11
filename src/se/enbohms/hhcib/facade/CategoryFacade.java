package se.enbohms.hhcib.facade;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.inject.Named;

import se.enbohms.hhcib.entity.Category;

@Named
public class CategoryFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Category> getCategories() {
		return Arrays.asList(Category.of("Bygg och fritid"),
				Category.of("Ekonomi"), Category.of("IT"));
	}
}
