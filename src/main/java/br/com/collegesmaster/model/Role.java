package br.com.collegesmaster.model;

import java.util.Set;

import br.com.collegesmaster.model.impl.UserImpl;

public interface Role extends Model {

	void setName(String name);

	String getName();

	void setUsers(Set<UserImpl> users);

	Set<UserImpl> getUsers();

}
