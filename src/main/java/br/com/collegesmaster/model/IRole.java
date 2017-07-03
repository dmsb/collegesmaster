package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.model.impl.Permission;

public interface IRole extends IModel {

	void setName(String name);

	String getName();

	void setPermissions(List<Permission> permissions);

	List<Permission> getPermissions();

}
