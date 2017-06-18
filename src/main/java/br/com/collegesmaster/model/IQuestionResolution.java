package br.com.collegesmaster.model;

import java.util.Set;

public interface IQuestionResolution extends IModel {

	void setTargetQuestion(IQuestion targetQuestion);

	IQuestion getTargetQuestion();

	void setAlternativesResolution(Set<IAlternativeResolution> alternativesResolution);

	Set<IAlternativeResolution> getAlternativesResolution();

	void setChallengeResolution(IChallengeResolution challengeResolution);

	IChallengeResolution getChallengeResolution();

}
