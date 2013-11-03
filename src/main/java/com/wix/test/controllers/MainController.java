package com.wix.test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wix.test.dao.CategoryDAO;

@Controller
public class MainController {

	@Autowired
	private CategoryDAO categoryDAO;

	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	@RequestMapping(value = "/")
	public ModelAndView mainPage(ModelMap map) {
		prepareList(map);
		return new ModelAndView("home");
	}

	@RequestMapping(value = "/index")
	public ModelAndView indexPage(ModelMap map) {
		prepareList(map);
		return new ModelAndView("home");
	}

	private void prepareList(ModelMap map) {
		map.addAttribute("category", new CategoryCommand());
		map.addAttribute("list", categoryDAO.getCategories());
		map.addAttribute("listRecursive", categoryDAO.getCategoriesRecursive());
		map.addAttribute("listIterative", categoryDAO.getCategoriesIterative());

	}

	@RequestMapping(value = "/add")
	public String add(
			@ModelAttribute(value = "category") CategoryCommand category,
			BindingResult result, ModelMap map) {

		categoryDAO.createCategory(category);
		prepareList(map);

		return "redirect:/";
	}

	@RequestMapping("/delete/{id}")
	public String deleteEmplyee(@PathVariable("id") Long id) {
		categoryDAO.delete(id);
		return "redirect:/";
	}
}
