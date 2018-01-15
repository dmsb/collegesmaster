package br.com.collegesmaster.model.institute.dataprovider.impl;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.jboss.ejb3.annotation.SecurityDomain;

import br.com.collegesmaster.model.institute.Institute;
import br.com.collegesmaster.model.institute.dataprovider.CourseDataProvider;
import br.com.collegesmaster.model.institute.impl.CourseImpl;
import br.com.collegesmaster.qualifiers.UserDatabase;

@Stateless
@TransactionManagement(CONTAINER)
@SecurityDomain("collegesmasterSecurityDomain")
@PermitAll
public class CourseDataProviderImpl implements CourseDataProvider {
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;

	@TransactionAttribute(REQUIRED)
	@Override
	public List<CourseImpl> findNamesByInstitute(Institute institute) {

		final CriteriaQuery<CourseImpl> criteriaQuery = cb.createQuery(CourseImpl.class);
		final Root<CourseImpl> rootCourse = criteriaQuery.from(CourseImpl.class);
		
		final List<Selection<?>> selections = new ArrayList<Selection<?>>();
		selections.add(rootCourse.get("id"));
		selections.add(rootCourse.get("name"));
		selections.add(rootCourse.get("version"));
		
		criteriaQuery.multiselect(selections);
		
		final Predicate institutePredicate = cb.equal(rootCourse.get("institute"), institute);
		criteriaQuery.where(institutePredicate);
		
		final TypedQuery<CourseImpl> typedQuery = em.createQuery(criteriaQuery);		
		final List<CourseImpl> result = typedQuery.getResultList();
		
		return result;
	}

}
