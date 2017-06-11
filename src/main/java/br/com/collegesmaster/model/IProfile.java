package br.com.collegesmaster.model;

import java.util.List;

public interface IProfile extends IModel {

	void setName(String name);

	String getName();

	void setPermissions(List<IPermission> permissions);

	List<IPermission> getPermissions();

}
