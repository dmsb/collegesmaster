package br.com.collegesmaster.model.challengeresponse.dataprovider;

import java.util.List;

import br.com.collegesmaster.model.challengeresponse.impl.RankingImpl;
import br.com.collegesmaster.model.security.User;

public interface RankingDataProvider {
	
	List<RankingImpl> findBestPositionsByPeriod(String semester);
	
	RankingImpl findByUserAndPeriod(User user, String semester);	
}
