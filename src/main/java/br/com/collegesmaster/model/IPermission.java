package br.com.collegesmaster.model;

import java.util.List;

public interface IPermission extends IModel {

	void setName(String name);

	String getName();

	void setProfiles(List<IProfile> profiles);

	List<IProfile> getProfiles();

}
