package br.com.collegesmaster.model.business.impls;

import static br.com.collegesmaster.rest.utils.RestUtils.buildPredicatesFromRequest;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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
import javax.ws.rs.core.UriInfo;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.logging.Logger;

import br.com.collegesmaster.model.business.ChallengeBusiness;
import br.com.collegesmaster.model.entities.challenge.Challenge;
import br.com.collegesmaster.model.entities.challenge.impl.ChallengeImpl;
import br.com.collegesmaster.model.entities.challenge.impl.ChallengeImpl_;
import br.com.collegesmaster.model.entities.question.impl.QuestionImpl;
import br.com.collegesmaster.model.entities.question.impl.QuestionImpl_;
import br.com.collegesmaster.model.entities.user.User;
import br.com.collegesmaster.qualifiers.UserDatabase;

@Stateless
@TransactionManagement(CONTAINER)
@SecurityDomain("collegesmasterSecurityDomain")
public class ChallengeBusinessImpl implements ChallengeBusiness {
	
	@Inject
	private Logger LOGGER;
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean create(final ChallengeImpl challenge) {
		if(challenge != null && challenge.getId() == null && challenge.getVersion() == null) {
			em.persist(challenge);
			return TRUE;
		} else {
			LOGGER.warn("Entity not persisted, invalid arguments");
			return FALSE;			
		}
	}
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public ChallengeImpl update(final ChallengeImpl challenge) {
		if(challenge != null && challenge.getId() != null && challenge.getVersion() != null) {
			return em.merge(challenge);
		} else {
			LOGGER.warn("Entity not persisted, invalid arguments");
			return null;
		}
	}
	
	@RolesAllowed({"ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean remove(final ChallengeImpl challenge) {
		
		if(challenge != null && challenge.getId() != null && challenge.getVersion() != null) {
			em.remove(challenge);				
			return TRUE;
		} else {
			LOGGER.warn("Entity not removed, invalid arguments");
			return FALSE;
		}
	}

	@RolesAllowed({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public ChallengeImpl findById(Integer id) {
		if(id != null) {
			return em.find(ChallengeImpl.class, id);			
		} else {
			LOGGER.warn("Cannot find entity, invalid arguments");
			return null;
		}
	}
	
	@RolesAllowed({"ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public List<ChallengeImpl> findAll() {
		
		final CriteriaQuery<ChallengeImpl> criteriaQuery = cb.createQuery(ChallengeImpl.class);
		final TypedQuery<ChallengeImpl> typedQuery = em.createQuery(criteriaQuery);		
		return typedQuery.getResultList(); 		
	}
	
	@RolesAllowed({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public List<ChallengeImpl> findAllByPredicates(final UriInfo filterInfo) {
		
		final Map<String, Object> equalsPredicate = buildPredicatesFromRequest(filterInfo.getQueryParameters());
		
		final CriteriaQuery<ChallengeImpl> criteriaQuery = cb.createQuery(ChallengeImpl.class);		
		final Root<ChallengeImpl> instituteRoot = criteriaQuery.from(ChallengeImpl.class);
		
		final List<Predicate> predicates = new ArrayList<>();
		equalsPredicate.forEach((key, value) -> predicates.add(cb.equal(instituteRoot.get(key), value)));

		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		final TypedQuery<ChallengeImpl> typedQuery = em.createQuery(criteriaQuery);		
		
		return typedQuery.getResultList();
	}
	
	@PermitAll
	@Override
	public List<QuestionImpl> findQuestionsByChallenge(final Challenge selectedChallenge) {

		final CriteriaQuery<QuestionImpl> criteriaQuery = cb.createQuery(QuestionImpl.class);
		final Root<QuestionImpl> questionRoot = criteriaQuery.from(QuestionImpl.class);
		
		final Predicate challengeCondition = 
				cb.equal(questionRoot.get(QuestionImpl_.challenge), selectedChallenge);
		
		criteriaQuery.where(challengeCondition);
		
		final TypedQuery<QuestionImpl> typedQuery = em.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATOR"})
	@Override
	public List<ChallengeImpl> findByUser(final User user) {

		final CriteriaQuery<ChallengeImpl> criteriaQuery = cb.createQuery(ChallengeImpl.class);
		final Root<ChallengeImpl> challengeRoot = criteriaQuery.from(ChallengeImpl.class);
		
		final Predicate predicate = cb.equal(challengeRoot.get(ChallengeImpl_.owner), user);
		
		criteriaQuery.where(predicate);
		
		final TypedQuery<ChallengeImpl> typedQuery = em.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}
}
