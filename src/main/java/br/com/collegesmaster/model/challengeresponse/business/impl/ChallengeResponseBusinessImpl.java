package br.com.collegesmaster.model.challengeresponse.business.impl;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
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

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.logging.Logger;

import br.com.collegesmaster.exceptions.BusinessException;
import br.com.collegesmaster.model.challenge.Challenge;
import br.com.collegesmaster.model.challengeresponse.business.ChallengeResponseBusiness;
import br.com.collegesmaster.model.challengeresponse.impl.ChallengeResponseImpl;
import br.com.collegesmaster.model.challengeresponse.impl.ChallengeResponseImpl_;
import br.com.collegesmaster.model.generics.producers.BooleanReponseFactory;
import br.com.collegesmaster.model.user.User;
import br.com.collegesmaster.qualifiers.UserDatabase;

@Stateless
@TransactionManagement(CONTAINER)
@SecurityDomain("collegesmasterSecurityDomain")
public class ChallengeResponseBusinessImpl  implements ChallengeResponseBusiness {
	
	@Inject
	private Logger LOGGER;
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	private CriteriaBuilder cb;
	
	@EJB
	private BooleanReponseFactory<ChallengeResponseImpl> booleanResponseBuilder;
	
	@RolesAllowed({"STUDENT", "ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean create(final ChallengeResponseImpl response) {
		if(response != null && response.getId() == null && response.getVersion() == null) {
			final Boolean alrealdyRepliedByUser = alrealdyRepliedByUser(response);
			if(Boolean.FALSE.equals(alrealdyRepliedByUser)) {
				em.persist(response);
				return TRUE;
			} else {
				throw new BusinessException("already_replied_challenge_message");
			}
		} else {
			LOGGER.error("Entity not persisted, invalid arguments");
		}
		return FALSE;
	}
	
	public Boolean alrealdyRepliedByUser(final ChallengeResponseImpl response) {
		final Root<ChallengeResponseImpl> crRoot = booleanResponseBuilder.build(ChallengeResponseImpl.class);
		final Predicate equalsUser = cb.equal(crRoot.get(ChallengeResponseImpl_.owner), response.getOwner());
		final Predicate equalsTargetChallenge = cb.equal(crRoot.get(ChallengeResponseImpl_.targetChallenge), 
				response.getTargetChallenge());
		return booleanResponseBuilder.where(equalsUser, equalsTargetChallenge).execute();
		
	}
	
	@RolesAllowed({"STUDENT", "ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public ChallengeResponseImpl update(final ChallengeResponseImpl response) {
		if(response != null && response.getId() != null && response.getVersion() != null) {
			return em.merge(response);
		} else {
			LOGGER.warn("Entity not persisted, invalid arguments");
			return null;
		}
	}
	
	@RolesAllowed({"ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean remove(final ChallengeResponseImpl response) {
		if(response != null && response.getId() != null && response.getVersion() != null) {
			em.remove(response);
			return TRUE;
		} else {
			LOGGER.warn("Entity not removed, invalid arguments");
			return FALSE;
		}
	}

	@RolesAllowed({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public ChallengeResponseImpl findById(final Integer id) {
		if(id != null) {
			return em.find(ChallengeResponseImpl.class, id);			
		} else {
			LOGGER.warn("Cannot find entity, invalid arguments");
			return null;
		}
	}
	
	@RolesAllowed({"ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public List<ChallengeResponseImpl> findAll() {
		final CriteriaQuery<ChallengeResponseImpl> criteriaQuery = cb.createQuery(ChallengeResponseImpl.class);
		final TypedQuery<ChallengeResponseImpl> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
	
	@RolesAllowed({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
	@Override
	public List<ChallengeResponseImpl> findAllByUser(final User user) {
		final CriteriaQuery<ChallengeResponseImpl> criteriaQuery = cb.createQuery(ChallengeResponseImpl.class);
		final Root<ChallengeResponseImpl> challengeResponseRoot = criteriaQuery.from(ChallengeResponseImpl.class);
		final Predicate userPredicate = cb.equal(challengeResponseRoot.get(ChallengeResponseImpl_.owner), user);
		
		criteriaQuery
			.select(challengeResponseRoot)
			.where(userPredicate);
		
		final TypedQuery<ChallengeResponseImpl> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
		
	}
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATOR"})
	@Override
	public List<ChallengeResponseImpl> findAllByChallenge(Challenge selectedChallenge) {
		final CriteriaQuery<ChallengeResponseImpl> criteriaQuery = cb.createQuery(ChallengeResponseImpl.class);
		final Root<ChallengeResponseImpl> challengeResponseRoot = criteriaQuery.from(ChallengeResponseImpl.class);
		challengeResponseRoot.fetch(ChallengeResponseImpl_.owner);
		
		final Predicate predicate = cb
				.equal(challengeResponseRoot.get(ChallengeResponseImpl_.targetChallenge), selectedChallenge);
		
		criteriaQuery
			.select(challengeResponseRoot)
			.where(predicate);
		
		final TypedQuery<ChallengeResponseImpl> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

}
