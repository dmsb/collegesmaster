package br.com.collegesmaster.model.challengeresponse.business.impl;

import static java.lang.Boolean.FALSE;
import static javax.ejb.TransactionAttributeType.MANDATORY;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.logging.Logger;

import br.com.collegesmaster.exceptions.BusinessException;
import br.com.collegesmaster.model.challenge.Challenge;
import br.com.collegesmaster.model.challengeresponse.business.ChallengeResponseBusiness;
import br.com.collegesmaster.model.challengeresponse.business.RankingBusiness;
import br.com.collegesmaster.model.challengeresponse.dataprovider.ChallengeResponseDataProvider;
import br.com.collegesmaster.model.challengeresponse.impl.ChallengeResponseImpl;
import br.com.collegesmaster.model.generics.impl.GenericBusinessImpl;
import br.com.collegesmaster.model.security.User;

@Stateless
@TransactionManagement(CONTAINER)
@SecurityDomain("collegesmasterSecurityDomain")
public class ChallengeResponseBusinessImpl extends GenericBusinessImpl<ChallengeResponseImpl> 
	implements ChallengeResponseBusiness {
	
	@Inject
	private Logger LOGGER;
	
	@Inject
	private ChallengeResponseDataProvider challengeResponseDataProvider;
	
	@Inject
	private RankingBusiness rankingBusiness;
	
	@RolesAllowed({"STUDENT", "ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean create(final ChallengeResponseImpl response) {
		final Boolean alrealdyRepliedByUser = alrealdyRepliedByUser(response);
		if(FALSE.equals(alrealdyRepliedByUser)) {
			final Boolean wasCreated = super.genericCreate(response);
			if(wasCreated) {
				rankingBusiness.addPunctuationToUser(response);
			}
			return wasCreated;
		} else {
			return FALSE;
		}
	}
	
	@RolesAllowed({"STUDENT", "ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public ChallengeResponseImpl update(final ChallengeResponseImpl response) {
		return super.genericUpdate(response);
	}
	
	@RolesAllowed({"ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean remove(final ChallengeResponseImpl response) {
		return super.genericRemove(response);
	}

	@RolesAllowed({"STUDENT", "ADMINISTRATOR"})
	@TransactionAttribute(MANDATORY)
	@Override
	public Boolean alrealdyRepliedByUser(final ChallengeResponseImpl response) {
		if(response != null && response.isNew()) {
			return challengeResponseDataProvider.alrealdyRepliedByUser(response);
		} else {
			LOGGER.warn("This challenge already registered");
			throw new BusinessException("this_challenge_already_registered_message");
		}
	}
	
	@RolesAllowed({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public ChallengeResponseImpl findById(final Integer id) {
		return super.findById(ChallengeResponseImpl.class, id);
	}
	
	@RolesAllowed({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public List<ChallengeResponseImpl> findAllByUser(final User user) {
		return challengeResponseDataProvider.findAllByUser(user);
		
	}
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public List<ChallengeResponseImpl> findAllByChallenge(Challenge selectedChallenge) {
		return challengeResponseDataProvider.findAllByChallenge(selectedChallenge);
	}

}
