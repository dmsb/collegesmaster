package br.com.collegesmaster.model.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.model.Model;
import br.com.collegesmaster.model.user.impl.RoleImpl;

@JsonDeserialize(as = RoleImpl.class)
public interface Role extends Model {

	void setName(String name);

	String getName();

}
