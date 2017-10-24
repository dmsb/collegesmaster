package br.com.collegesmaster.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.impl.RoleImpl;

@JsonDeserialize(as = RoleImpl.class)
public interface Role extends Model {

	void setName(String name);

	String getName();

}
