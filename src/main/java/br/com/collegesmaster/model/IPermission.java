package br.com.collegesmaster.model;

import java.util.List;

public interface IPermission extends IModel {

	public void setName(String name);

	public String getName();

	public void setProfiles(List<IProfile> profiles);

	public List<IProfile> getProfiles();

}
