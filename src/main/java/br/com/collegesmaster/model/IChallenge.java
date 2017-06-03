package br.com.collegesmaster.model;

import br.com.collegesmaster.enums.Alternative;
import br.com.collegesmaster.enums.ChallengeLevel;

public interface IChallenge extends IModel {

	IUser getUser();

	void setUser(IUser user);

	IDiscipline getDiscipline();

	void setDiscipline(IDiscipline discipline);

	Alternative getResponse();

	void setResponse(Alternative response);

	ChallengeLevel getLevel();

	void setLevel(ChallengeLevel level);

	byte[] getAttachment();

	void setAttachment(byte[] attachment);

	String getFileName();

	void setFileName(String fileName);

	Integer getPontuation();

	void setPontuation(Integer pontuation);

}