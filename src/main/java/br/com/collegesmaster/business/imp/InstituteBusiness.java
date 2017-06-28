package br.com.collegesmaster.business.imp;

import static br.com.collegesmaster.model.imp.Institute_.courses;
import static br.com.collegesmaster.model.imp.Institute_.id;
import static br.com.collegesmaster.model.imp.Institute_.name;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import br.com.collegesmaster.business.IInstituteBusiness;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.imp.Institute;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class InstituteBusiness extends GenericBusiness implements IInstituteBusiness {

	@Override
	public void persist(IInstitute institute) {
		entityManager.persist(institute);
		
	}

	@Override
	public IInstitute merge(IInstitute institute) {
		return entityManager.merge(institute);
		
	}

	@Override
	public void remove(IInstitute institute) {
		entityManager.remove(institute);
		
	}

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