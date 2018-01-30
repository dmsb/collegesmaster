package br.com.collegesmaster.model.challengeresponse;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.challengeresponse.impl.RankingImpl;
import br.com.collegesmaster.model.institute.Discipline;
import br.com.collegesmaster.model.model.Model;
import br.com.collegesmaster.model.security.User;

@JsonDeserialize(as = RankingImpl.class)
public interface Ranking extends Model {

	void setUser(User user);

	User getUser();

	void setDiscipline(Discipline discipline);

	Discipline getDiscipline();

	void setTotalPontuation(Integer totalPontuation);

	Integer getTotalPontuation();
	
}
