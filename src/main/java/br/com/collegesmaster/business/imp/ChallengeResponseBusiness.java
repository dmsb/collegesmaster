package br.com.collegesmaster.business.imp;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.collegesmaster.business.IChallengeResponseBusiness;
import br.com.collegesmaster.model.IChallengeResponse;
import br.com.collegesmaster.model.imp.ChallengeResponse;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ChallengeResponseBusiness extends GenericBusiness implements IChallengeResponseBusiness {

	@Override
	public void persist(IChallengeResponse response) {
		entityManager.persist(response);
	}

	@Override
	public IChallengeResponse merge(IChallengeResponse response) {
		return entityManager.merge(response);
	}

	@Override
	public void remove(IChallengeResponse response) {
		entityManager.remove(response);
	}

	@Override
	public IChallengeResponse findById(Integer id) {
		return entityManager.find(ChallengeResponse.class, id);
	}

	@Override
	public List<ChallengeResponse> findAll() {
		final CriteriaQuery<ChallengeResponse> criteriaQuery = builder.createQuery(ChallengeResponse.class);
		final TypedQuery<ChallengeResponse> typedQuery = entityManager.createQuery(criteriaQuery);		
		return typedQuery.getResultList();
	}

}
