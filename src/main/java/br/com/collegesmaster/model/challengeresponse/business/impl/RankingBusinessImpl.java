package br.com.collegesmaster.model.challengeresponse.business.impl;

import static javax.ejb.TransactionAttributeType.MANDATORY;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.jboss.security.annotation.SecurityDomain;

import com.google.common.base.Strings;

import br.com.collegesmaster.exceptions.BusinessException;
import br.com.collegesmaster.model.challengeresponse.ChallengeResponse;
import br.com.collegesmaster.model.challengeresponse.business.RankingBusiness;
import br.com.collegesmaster.model.challengeresponse.dataprovider.RankingDataProvider;
import br.com.collegesmaster.model.challengeresponse.impl.RankingImpl;
import br.com.collegesmaster.model.generics.impl.GenericBusinessImpl;
import br.com.collegesmaster.model.institute.Institute;
import br.com.collegesmaster.model.security.User;

@Stateless
@TransactionManagement(CONTAINER)
@SecurityDomain("collegesmasterSecurityDomain")
@RolesAllowed({"ADMINISTRATOR", "STUDENT"})
public class RankingBusinessImpl extends GenericBusinessImpl<RankingImpl> implements RankingBusiness {
	
	@Inject
	private RankingDataProvider rankingDataProvider;
	
	@TransactionAttribute(MANDATORY)
	@Override
	public Boolean create(RankingImpl ranking) {
		return super.genericCreate(ranking);
	}

	@TransactionAttribute(MANDATORY)
	@Override
	public RankingImpl update(RankingImpl ranking) {
		return super.genericUpdate(ranking);
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean remove(RankingImpl ranking) {
		return super.genericRemove(ranking);
	}
	
	@TransactionAttribute(REQUIRED)
	@Override
	public List<RankingImpl> findBestPositionsByPeriod(String period, final Institute userInstitute) {
		if(!Strings.isNullOrEmpty(period)) {
			return rankingDataProvider.findBestPositionsByPeriod(period, userInstitute);	
		} else {
			throw new BusinessException("provide_a_period_message");
		}
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	@Override
	public void addPontuationToUser(ChallengeResponse challengeResponse) {
		if(challengeResponse != null && challengeResponse.getOwner() != null) {
			final RankingImpl userRankingInPeriod = findByUserAndPeriod(challengeResponse.getOwner(), 
					challengeResponse.getTargetChallenge()
						.getDiscipline().getCourse()
						.getInstitute().getSemester());
			if(userRankingInPeriod != null) {
				final Integer currentPontuation = userRankingInPeriod.getTotalPontuation();
				final Integer currentChallengeResponsePontuation = challengeResponse.getPontuation();
				userRankingInPeriod.setTotalPontuation(currentPontuation + currentChallengeResponsePontuation);
				this.update(userRankingInPeriod);
			} else {
				final RankingImpl newUserRanking = buildNewRanking(challengeResponse);
				this.create(newUserRanking);
			}
		}
	}

	private RankingImpl buildNewRanking(ChallengeResponse challengeResponse) {
		final RankingImpl newUserRanking = new RankingImpl();
		newUserRanking.setDiscipline(challengeResponse.getTargetChallenge().getDiscipline());
		newUserRanking.setTotalPontuation(challengeResponse.getPontuation());
		newUserRanking.setUser(challengeResponse.getOwner());
		return newUserRanking;
	}
	
	@TransactionAttribute(REQUIRED)
	@Override
	public RankingImpl findByUserAndPeriod(User user, String period) {
		return rankingDataProvider.findByUserAndPeriod(user, period);
	}

}
