package br.com.collegesmaster.business.impl;

import static br.com.collegesmaster.model.impl.Question_.challenge;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.Path;

import org.jboss.ejb3.annotation.SecurityDomain;

import br.com.collegesmaster.annotation.qualifier.UserDatabase;
import br.com.collegesmaster.business.IChallengeBusiness;
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.impl.Challenge;
import br.com.collegesmaster.model.impl.Challenge_;
import br.com.collegesmaster.model.impl.Question;

@Path("/challenge")
@Stateless
@TransactionManagement(CONTAINER)
@SecurityDomain("collegesmasterSecurityDomain")
public class ChallengeBusiness implements IChallengeBusiness {
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATOR"})
	@Override
	public void create(final IChallenge challenge) {
		em.persist(challenge);
	}
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATOR"})
	@Override
	public IChallenge update(final IChallenge challenge) {
		return em.merge(challenge);
	}
	
	@RolesAllowed({"ADMINISTRATOR"})
	@Override
	public void remove(final IChallenge challenge) {
		em.remove(challenge);
	}

	@RolesAllowed({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
	@Override
	public IChallenge findById(Integer id) {
		return em.find(Challenge.class, id);
	}
	
	@RolesAllowed({"ADMINISTRATOR"})
	@Override
	public List<Challenge> findAll() {
		
		final CriteriaQuery<Challenge> criteriaQuery = cb.createQuery(Challenge.class);
		final TypedQuery<Challenge> typedQuery = em.createQuery(criteriaQuery);		
		return typedQuery.getResultList(); 		
	}

	@PermitAll
	@Override
	public List<Question> findQuestionsByChallenge(final IChallenge selectedChallenge) {

		final CriteriaQuery<Question> criteriaQuery = cb.createQuery(Question.class);
		final Root<Question> questionRoot = criteriaQuery.from(Question.class);
		
		final Predicate challengeCondition = 
				cb.equal(questionRoot.get(challenge), selectedChallenge);
		
		criteriaQuery.where(challengeCondition);
		
		final TypedQuery<Question> typedQuery = em.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATOR"})
	@Override
	public List<Challenge> findByUser(final IUser user) {
		final CriteriaQuery<Challenge> criteriaQuery = cb.createQuery(Challenge.class);
		final Root<Challenge> challengeRoot = criteriaQuery.from(Challenge.class);
		
		final Predicate predicate = cb.equal(challengeRoot.get(Challenge_.owner), user);
		
		criteriaQuery.where(predicate);
		
		final TypedQuery<Challenge> typedQuery = em.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}	
}
