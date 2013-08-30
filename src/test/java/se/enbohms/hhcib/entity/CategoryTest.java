package se.enbohms.hhcib.entity;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Test client for the {@link Category} enum
 * 
 */
public class CategoryTest {

	private static final int ENOUGH_RANDOM = 50;

	@Test
	public void should_return_randon_category() throws Exception {
		List<Category> randomCategories = new ArrayList<>();
		for (int i = 0; i < ENOUGH_RANDOM; i++) {
			randomCategories.add(Category.getRandom());
		}

		assertThat(
				randomCategories.containsAll(Arrays.asList(Category.values())))
				.isTrue();
	}
}