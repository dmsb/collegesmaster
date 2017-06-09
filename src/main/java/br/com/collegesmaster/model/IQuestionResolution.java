package br.com.collegesmaster.model;

import java.util.List;

public interface IQuestionResolution extends IModel {

	void setTargetQuestion(IQuestion targetQuestion);

	IQuestion getTargetQuestion();

	void setAlternativesResolution(List<IAlternativeResolution> alternativesResolution);

	List<IAlternativeResolution> getAlternativesResolution();

	void setChallengeResolution(IChallengeResolution challengeResolution);

	IChallengeResolution getChallengeResolution();

}
