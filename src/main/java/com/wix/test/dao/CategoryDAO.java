package com.wix.test.dao;

import java.util.List;

import com.wix.test.controllers.CategoryCommand;
import com.wix.test.controllers.Categoryindented;
import com.wix.test.model.Category;

public interface CategoryDAO {
	public List<Category> getCategories();
	
	public List<Categoryindented> getCategoriesRecursive();
	
	public List<Categoryindented> getCategoriesIterative();

	public void createCategory(CategoryCommand category);

	public void delete(Long id);
}
