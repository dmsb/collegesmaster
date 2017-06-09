package br.com.collegesmaster.model;

public interface IUser extends IModel {

	public String getSalt();

	public void setSalt(String salt);

	public String getUsername();

	public void setUsername(String username);

	public String getPassword();

	public void setPassword(String password);

	public void setProfile(IProfile profile);

	public IProfile getProfile();

	void setGeneralInfo(IGeneralInfo person);

	IGeneralInfo getGeneralInfo();

}