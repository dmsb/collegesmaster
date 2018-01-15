package br.com.collegesmaster.model.security;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.model.Model;
import br.com.collegesmaster.model.security.impl.RoleImpl;

@JsonDeserialize(as = RoleImpl.class)
public interface Role extends Model {

	void setName(String name);

	String getName();

}
