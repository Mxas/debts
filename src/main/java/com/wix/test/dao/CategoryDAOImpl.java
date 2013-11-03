package com.wix.test.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;

import com.wix.test.controllers.CategoryCommand;
import com.wix.test.controllers.Categoryindented;
import com.wix.test.model.Category;

@Repository
public class CategoryDAOImpl implements CategoryDAO {
	private EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("todos");

	@Override
	public List<Category> getCategories() {

		EntityManager em = emf.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Category> q = cb.createQuery(Category.class);
		q.from(Category.class);

		TypedQuery<Category> r = em.createQuery(q);
		return r.getResultList();
	}

	@Override
	public void createCategory(CategoryCommand category) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		Category test = new Category();
		test.setDescription(category.getDescription());
		if (category.getParentId() != null) {
			test.setParent(em.find(Category.class, category.getParentId()));
		}
		tx.begin();
		em.persist(test);
		tx.commit();
		em.close();

	}

	@Override
	public List<Categoryindented> getCategoriesRecursive() {
		List<Category> cource = new ArrayList<Category>(getCategories());
		List<Categoryindented> results = new ArrayList<Categoryindented>();
		for (Category cat : cource) {
			if (cat.getParent() == null) {
				results.add(new Categoryindented(cat, 0));
				sort(results, cat.getSubCategories(), 1);
			}
		}

		return results;
	}

	private void sort(List<Categoryindented> results, List<Category> cource,
			int i) {
		if (cource != null)
			for (Category cat : cource) {
				results.add(new Categoryindented(cat, i));

				sort(results, cat.getSubCategories(), i + 1);
			}

	}

	@Override
	public List<Categoryindented> getCategoriesIterative() {
		List<Categoryindented> roots = new ArrayList<Categoryindented>();
		List<Category> childrens = new ArrayList<Category>();
		List<Category> cource = new ArrayList<Category>(getCategories());

		for (Category cat : cource) {
			if (cat.getParent() == null) {
				roots.add(new Categoryindented(cat));
			} else {
				childrens.add(cat);
			}
		}
		while (!childrens.isEmpty()) {
			for (Category c : new ArrayList<Category>(childrens)) {
				for (Categoryindented root : new ArrayList<Categoryindented>(
						roots)) {
					if (c.getParent().getId().equals(root.getId())) {
						childrens.remove(c);
						int index = roots.indexOf(root);
						roots = makeList(roots, index, c);
						break;
					}
				}
			}
		}

		// for (Categoryindented root : new ArrayList<Categoryindented>(roots))
		// {
		//
		// }

		// for (Category c : new ArrayList<Category>(childrens)) {
		// if (c.getParent().getId())
		// }
		//
		//
		// for (Categoryindented root : roots) {
		// results.add(root);
		// for (Category c : new ArrayList<Category>(childrens)) {
		// if (c.getParent().get)
		// }
		// while (!childrens.isEmpty()) {
		// Category chld = childrens.remove(0);
		// }
		// }
		return roots;
	}

	private List<Categoryindented> makeList(List<Categoryindented> roots,
			int index, Category c) {
		List<Categoryindented> results = new ArrayList<Categoryindented>();
		int i = 0;
		for (Categoryindented categoryindented : roots) {
			results.add(categoryindented);
			
			if (i == index) {
				results.add(new Categoryindented(c, findIndent(c)));
			}
			i++;
		}
		return results;
	}

	private int findIndent(Category c) {
		int i = 0;
		Category parent = c.getParent();
		while (parent != null) {
			i++;
			parent = parent.getParent();
		}
		return i;
	}

	@Override
	public void delete(Long id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		Category e = em.find(Category.class, id);
		if (e == null)
			return;
		tx.begin();
		em.remove(e);
		tx.commit();
		em.close();

	}
}
