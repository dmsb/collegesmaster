package br.com.collegesmaster.model.institute.dataprovider.impl;

import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.jboss.ejb3.annotation.SecurityDomain;

import br.com.collegesmaster.model.institute.dataprovider.InstituteDataProvider;
import br.com.collegesmaster.model.institute.impl.InstituteImpl;
import br.com.collegesmaster.model.institute.impl.InstituteImpl_;
import br.com.collegesmaster.qualifiers.UserDatabase;

@Stateless
@TransactionManagement(CONTAINER)
@PermitAll
@SecurityDomain("collegesmasterSecurityDomain")
public class InstituteDataProviderImpl implements InstituteDataProvider {

	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@Override
	public List<InstituteImpl> findNames() {
		final CriteriaQuery<InstituteImpl> criteriaQuery = cb.createQuery(InstituteImpl.class);
		
		final Root<InstituteImpl> rootInstitute = criteriaQuery.from(InstituteImpl.class);
		
		final List<Selection<?>> selections = new ArrayList<>();
		selections.add(rootInstitute.get(InstituteImpl_.id));		
		selections.add(rootInstitute.get(InstituteImpl_.name));
		selections.add(rootInstitute.get(InstituteImpl_.version));
		criteriaQuery.multiselect(selections);

		final TypedQuery<InstituteImpl> typedQuery = em.createQuery(criteriaQuery); 
		
		return typedQuery.getResultList();
	}

	@Override
	public List<InstituteImpl> findFetchingCourses() {
		final CriteriaQuery<InstituteImpl> criteriaQuery = cb.createQuery(InstituteImpl.class);
		final Root<InstituteImpl> instituteRoot = criteriaQuery.from(InstituteImpl.class);
				
		instituteRoot.fetch(InstituteImpl_.courses);
		criteriaQuery.select(instituteRoot).distinct(true);
		
		final TypedQuery<InstituteImpl> typedQuery = em.createQuery(criteriaQuery);		
		
		return typedQuery.getResultList();
	}

}
