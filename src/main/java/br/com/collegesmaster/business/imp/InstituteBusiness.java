package br.com.collegesmaster.business.imp;

import static br.com.collegesmaster.model.impl.Institute_.courses;
import static br.com.collegesmaster.model.impl.Institute_.name;
import static br.com.collegesmaster.model.impl.Model_.id;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import br.com.collegesmaster.business.IInstituteBusiness;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.impl.Institute;

@Stateless
@TransactionManagement(CONTAINER)
@DeclareRoles({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
@RolesAllowed({"ADMINISTRATOR"})
public class InstituteBusiness extends GenericBusiness implements IInstituteBusiness {

	@Override
	public void save(IInstitute institute) {
		entityManager.persist(institute);
		
	}

	@Override
	public IInstitute update(IInstitute institute) {
		return entityManager.merge(institute);
		
	}

	@Override
	public void remove(IInstitute institute) {
		entityManager.remove(institute);
		
	}

	@PermitAll
	@Override
	public IInstitute findById(Integer id) {
		return entityManager.find(Institute.class, id);
	}
	
	@Override
	public List<Institute> findAll() {
		final CriteriaQuery<Institute> criteriaQuery = builder.createQuery(Institute.class);		
		criteriaQuery.from(Institute.class);
		
		final TypedQuery<Institute> typedQuery = entityManager.createQuery(criteriaQuery);		
		
		return typedQuery.getResultList();
	}
	
	@PermitAll
	@Override
	public List<Institute> findIdsAndNames() {
		final CriteriaQuery<Institute> criteriaQuery = builder.createQuery(Institute.class);
		
		final Root<Institute> rootInstitute = criteriaQuery.from(Institute.class);
		
		final List<Selection<?>> idAndNameSelections = new ArrayList<>();
		idAndNameSelections.add(rootInstitute.get(id));		
		idAndNameSelections.add(rootInstitute.get(name));
		
		criteriaQuery.multiselect(idAndNameSelections);

		final TypedQuery<Institute> typedQuery = entityManager.createQuery(criteriaQuery); 
		
		return typedQuery.getResultList();
	}
	
	@PermitAll
	@Override
	public List<Institute> findFetchingCourses() {
		final CriteriaQuery<Institute> criteriaQuery = builder.createQuery(Institute.class);
		final Root<Institute> instituteRoot = criteriaQuery.from(Institute.class);
				
		instituteRoot.fetch(courses);
		criteriaQuery.select(instituteRoot).distinct(true);
		//"If DISTINCT is not specified, duplicate values are not eliminated." (section 4.8 of JPA v2.0)
		
		final TypedQuery<Institute> typedQuery = entityManager.createQuery(criteriaQuery);		
		
		return typedQuery.getResultList();
	}
	
}