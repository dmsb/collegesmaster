package br.com.collegesmaster.model.challengeresponse.business.impl;

import static javax.ejb.TransactionAttributeType.MANDATORY;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.jboss.security.annotation.SecurityDomain;

import br.com.collegesmaster.exceptions.BusinessException;
import br.com.collegesmaster.model.challengeresponse.ChallengeResponse;
import br.com.collegesmaster.model.challengeresponse.Ranking;
import br.com.collegesmaster.model.challengeresponse.business.RankingBusiness;
import br.com.collegesmaster.model.challengeresponse.dataprovider.RankingDataProvider;
import br.com.collegesmaster.model.challengeresponse.impl.RankingImpl;
import br.com.collegesmaster.model.generics.impl.GenericBusinessImpl;
import br.com.collegesmaster.model.institute.Institute;
import br.com.collegesmaster.model.security.User;

@Stateless
@TransactionManagement(CONTAINER)
@SecurityDomain("collegesmasterSecurityDomain")
@RolesAllowed({"STUDENT", "ADMINISTRATOR"})
public class RankingBusinessImpl extends GenericBusinessImpl<Ranking> implements RankingBusiness {
	
	@Inject
	private RankingDataProvider rankingDataProvider;
	
	@TransactionAttribute(MANDATORY)
	@Override
	public Boolean create(final Ranking ranking) {
		return super.genericCreate(ranking);
	}

	@TransactionAttribute(MANDATORY)
	@Override
	public Ranking update(final Ranking ranking) {
		return super.genericUpdate(ranking);
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean remove(final Ranking ranking) {
		return super.genericRemove(ranking);
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public List<RankingImpl> findBestPositionsBySemester(final String semester, final Institute userInstitute) {
		if(semester != null) {
			return rankingDataProvider.findBestPositionsByPeriod(semester, userInstitute);	
		} else {
			throw new BusinessException("provide_a_semester_message");
		}
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	@Override
	public void addPunctuationToUser(final ChallengeResponse challengeResponse) {
		if(challengeResponse != null && challengeResponse.getOwner() != null) {
			final RankingImpl userRankingInPeriod = findByUserAndPeriod(challengeResponse.getOwner(), 
					challengeResponse.getTargetChallenge()
						.getDiscipline().getCourse()
						.getInstitute().getSemester());
			if(userRankingInPeriod != null) {
				final Integer currentPontuation = addStudentPontuation(challengeResponse.getPunctuation(), 
						userRankingInPeriod.getTotalPunctuation());
				userRankingInPeriod.setTotalPunctuation(currentPontuation);
				this.update(userRankingInPeriod);
			} else {
				final RankingImpl newUserRanking = buildANewRanking(challengeResponse);
				this.create(newUserRanking);
			}
		}
	}

	private Integer addStudentPontuation(final Integer currentChallengeResponsePunctuation,
			final Integer currentPunctuation) {
		return currentPunctuation + currentChallengeResponsePunctuation;
	}

	private RankingImpl buildANewRanking(final ChallengeResponse challengeResponse) {
		final RankingImpl newUserRanking = new RankingImpl();
		newUserRanking.setDiscipline(challengeResponse.getTargetChallenge().getDiscipline());
		newUserRanking.setTotalPunctuation(challengeResponse.getPunctuation());
		newUserRanking.setUser(challengeResponse.getOwner());
		return newUserRanking;
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public RankingImpl findByUserAndPeriod(final User user, final String period) {
		return rankingDataProvider.findByUserAndPeriod(user, period);
	}

}
