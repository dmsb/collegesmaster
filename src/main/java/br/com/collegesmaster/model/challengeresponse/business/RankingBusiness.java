package br.com.collegesmaster.model.challengeresponse.business;

import java.util.List;

import br.com.collegesmaster.model.challengeresponse.ChallengeResponse;
import br.com.collegesmaster.model.challengeresponse.impl.RankingImpl;
import br.com.collegesmaster.model.generics.GenericCRUD;
import br.com.collegesmaster.model.institute.Institute;
import br.com.collegesmaster.model.security.User;

public interface RankingBusiness extends GenericCRUD<RankingImpl> {
	
	List<RankingImpl> findBestPositionsBySemester(String semester, Institute userInstitute);
	
	void addPunctuationToUser(ChallengeResponse challengeResponse);
	
	RankingImpl findByUserAndPeriod(User user, String semester);
}
