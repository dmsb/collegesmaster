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

	public void setChallengesResponse(List<IChallengeResponse> completedChallenges);

	public List<IChallengeResponse> getChallengesResponse();

	public void setProfile(IProfile profile);

	public IProfile getProfile();

	public void setNotePerDiscipline(Map<IDiscipline, Integer> notePerDiscipline);

	public Map<IDiscipline, Integer> getNotePerDiscipline();

	public void setPerson(IPerson person);

	public IPerson getPerson();

}