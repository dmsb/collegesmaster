package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.model.imp.Permission;

public interface IProfile extends IModel {

	void setName(String name);

	String getName();

	void setPermissions(List<Permission> permissions);

	List<Permission> getPermissions();

}
