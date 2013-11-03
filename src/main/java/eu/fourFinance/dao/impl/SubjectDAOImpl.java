package eu.fourFinance.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import eu.fourFinance.dao.SubjectDAO;
import eu.fourFinance.model.Subject;

@Repository
@Transactional
public class SubjectDAOImpl implements SubjectDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Subject getSubject(String code) {
		Assert.hasLength(code);

		TypedQuery<Subject> q = em.createNamedQuery(Subject.SUBJECT_BY_CODE,
				Subject.class).setParameter("code", code);
		Subject subject = q.getSingleResult();
		if (subject == null) {
			subject = createSubject(code);
			subject.setCode(code);
			em.persist(subject);
		}
		return subject;
	}

	@Override
	public Subject createSubject(String code) {

		Subject subject = new Subject();
		subject.setCode(code);
		em.persist(subject);
		return subject;
	}

}
