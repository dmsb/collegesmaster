package br.com.collegesmaster.business.imp;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.collegesmaster.business.IChallengeBusiness;
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.imp.Challenge;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ChallengeBusiness extends GenericBusiness implements IChallengeBusiness {	
	
	@Override
	public void persist(final IChallenge challenge) {
		entityManager.persist(challenge);		
	}
	
	@Override
	public void merge(final IChallenge challenge) {
		entityManager.merge(challenge);
	}

	@Override
	public void remove(final IChallenge challenge) {
		entityManager.remove(challenge);
	}

	@Override
	public IChallenge findById(Integer id, Class<Challenge> modelClass) {
		return entityManager.find(modelClass, id);
	}

	@Override
	public List<Challenge> getList() {
		
		final CriteriaQuery<Challenge> criteriaQuery = criteriaBuilder.createQuery(Challenge.class);
		final TypedQuery<Challenge> typedQuery = entityManager.createQuery(criteriaQuery);		
		final List<Challenge> result = typedQuery.getResultList(); 
		return result;
	}
}
