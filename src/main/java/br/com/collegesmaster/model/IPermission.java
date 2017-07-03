package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.model.impl.Role;

public interface IPermission extends IModel {

	void setName(String name);

	String getName();

	void setRoles(List<Role> profiles);

	List<Role> getRoles();

}
