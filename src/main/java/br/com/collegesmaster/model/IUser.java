package br.com.collegesmaster.model;

import java.util.List;
import java.util.Map;

public interface IUser extends IModel {

	public String getSalt();

	public void setSalt(String salt);

	public String getUsername();

	public void setUsername(String username);

	public String getPassword();

	public void setPassword(String password);

	public IPerson getGeneralInfo();

	public void setGeneralInfo(IPerson person);

	public void setCompletedChallenges(List<ICompletedChallenge> completedChallenges);

	public List<ICompletedChallenge> getCompletedChallenges();

	public void setProfile(IProfile profile);

	public IProfile getProfile();

	public void setChallengesCreated(List<IChallenge> challengesCreated);

	public List<IChallenge> getChallengesCreated();

	void setNotePerDiscipline(Map<IDiscipline, Integer> notePerDiscipline);

	Map<IDiscipline, Integer> getNotePerDiscipline();

	void setPerson(IPerson person);

	IPerson getPerson();

}