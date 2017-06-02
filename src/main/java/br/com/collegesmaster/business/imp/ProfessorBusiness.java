package br.com.collegesmaster.business.imp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.collections.CollectionUtils;

import br.com.collegesmaster.business.IProfessorBusiness;
import br.com.collegesmaster.model.IProfessor;
import br.com.collegesmaster.model.imp.Professor;

@Stateless
public class ProfessorBusiness extends Business implements IProfessorBusiness {
	
	@PersistenceUnit(unitName = "collegesmasterPU")
	protected static EntityManagerFactory entityManagerFactory;	
	
	@Override
	@PostConstruct
	public void init() {
    	entityManager = entityManagerFactory.createEntityManager();
    	criteriaBuilder = entityManager.getCriteriaBuilder();
    }
	
	@Override
	@PreDestroy
	public void cleanup() {
    	if(entityManager.isOpen()) {
    		entityManager.close();
    	}
    }
	
	@Override
	public void persist(IProfessor imodel) {
		entityManager.persist(imodel);

	}

	@Override
	public void merge(IProfessor imodel) {
		entityManager.merge(imodel);

	}

	@Override
	public void remove(IProfessor imodel) {
		entityManager.remove(imodel);

	}

	@Override
	public IProfessor findById(Integer id, Class<IProfessor> modelClass) {
		return entityManager.find(modelClass, id);
	}

	@Override
	public List<IProfessor> getList() {
		final CriteriaQuery<Professor> criteriaQuery = criteriaBuilder.createQuery(Professor.class);
		final TypedQuery<Professor> typedQuery = entityManager.createQuery(criteriaQuery);		
		final List<Professor> result = typedQuery.getResultList(); 
		
		if(CollectionUtils.isEmpty(result)) {
			return null;
		} else {
			final List<IProfessor> professors = new ArrayList<IProfessor>();
			result.forEach(professor -> professors.add(professor));
			return professors;
		}
	}

}
