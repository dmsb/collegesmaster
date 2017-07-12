package br.com.collegesmaster.business.imp;

import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.collegesmaster.business.IChallengeResponseBusiness;
import br.com.collegesmaster.model.IChallengeResponse;
import br.com.collegesmaster.model.impl.ChallengeResponse;

@Stateless
@TransactionManagement(CONTAINER)
@DeclareRoles({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
@RolesAllowed({"ADMINISTRATOR"})
public class ChallengeResponseBusiness extends GenericBusiness implements IChallengeResponseBusiness {

	@RolesAllowed({"STUDENT", "ADMINISTRATOR"})
	@Override
	public void save(IChallengeResponse response) {
		entityManager.persist(response);
	}

	@RolesAllowed({"STUDENT", "ADMINISTRATOR"})
	@Override
	public IChallengeResponse update(IChallengeResponse response) {
		return entityManager.merge(response);
	}

	@Override
	public void remove(IChallengeResponse response) {
		entityManager.remove(response);
	}

	@RolesAllowed({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
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
