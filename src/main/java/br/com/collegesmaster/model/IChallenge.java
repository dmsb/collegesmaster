package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.enums.ChallengeType;

public interface IChallenge extends IModel {

	IUser getOwner();

	void setOwner(IUser user);

	IDiscipline getDiscipline();

	void setDiscipline(IDiscipline discipline);

	void setQuestions(List<IQuestion> questions);

	List<IQuestion> getQuestions();

	void setTitle(String title);

	String getTitle();

	void setChallengeType(ChallengeType challengeType);

	ChallengeType getChallengeType();
	
}