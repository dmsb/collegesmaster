package br.com.collegesmaster.model.entities.role;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.entities.model.Model;
import br.com.collegesmaster.model.entities.role.impl.RoleImpl;

@JsonDeserialize(as = RoleImpl.class)
public interface Role extends Model {

	void setName(String name);

	String getName();

}
