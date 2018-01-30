package br.com.collegesmaster.model.challenge.business.impl;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.jboss.ejb3.annotation.SecurityDomain;

import br.com.collegesmaster.model.challenge.Challenge;
import br.com.collegesmaster.model.challenge.business.ChallengeBusiness;
import br.com.collegesmaster.model.challenge.dataprovider.ChallengeDataProvider;
import br.com.collegesmaster.model.challenge.impl.ChallengeImpl;
import br.com.collegesmaster.model.challenge.impl.QuestionImpl;
import br.com.collegesmaster.model.generics.impl.GenericBusinessImpl;
import br.com.collegesmaster.model.security.User;

@Stateless
@TransactionManagement(CONTAINER)
@SecurityDomain("collegesmasterSecurityDomain")
public class ChallengeBusinessImpl extends GenericBusinessImpl<ChallengeImpl> implements ChallengeBusiness {
	
	@Inject
	private ChallengeDataProvider challengeDataProvider;
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean create(final ChallengeImpl challenge) {
		return super.genericCreate(challenge);
	}
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public ChallengeImpl update(final ChallengeImpl challenge) {
		return super.genericUpdate(challenge);
	}
	
	@RolesAllowed({"ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean remove(final ChallengeImpl challenge) {
		return super.genericRemove(challenge);
	}

	@RolesAllowed({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public Challenge findById(Integer id) {
		return super.findById(ChallengeImpl.class, id);
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public List<QuestionImpl> findQuestionsByChallenge(final Challenge selectedChallenge) {
		return challengeDataProvider.findQuestionsByChallenge(selectedChallenge);
	}
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public List<ChallengeImpl> findByUser(final User user) {
		return challengeDataProvider.findByUser(user);
	}
}
