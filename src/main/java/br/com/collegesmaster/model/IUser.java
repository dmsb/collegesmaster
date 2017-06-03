package br.com.collegesmaster.model;

import java.util.List;

public interface IUser extends IModel {

	String getSalt();

	void setSalt(String salt);

	String getUsername();

	void setUsername(String username);

	String getPassword();

	void setPassword(String password);

	IGeneralInfo getGeneralInfo();

	void setGeneralInfo(IGeneralInfo generalInfo);

	public abstract void setDisciplines(List<IDiscipline> disciplines);

	public abstract List<IDiscipline> getDisciplines();

	public abstract void setCompletedChallenges(List<IChallenge> completedChallenges);

	public abstract List<IChallenge> getCompletedChallenges();

	public abstract void setProfile(IProfile profile);

	public abstract IProfile getProfile();

	public abstract void setChallengesCreated(List<IChallenge> challengesCreated);

	public abstract List<IChallenge> getChallengesCreated();

}