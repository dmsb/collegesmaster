package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.model.impl.Profile;

public interface IPermission extends IModel {

	void setName(String name);

	String getName();

	void setProfiles(List<Profile> profiles);

	List<Profile> getProfiles();

}
