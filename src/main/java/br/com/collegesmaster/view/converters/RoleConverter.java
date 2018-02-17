package br.com.collegesmaster.view.converters;

import javax.faces.convert.FacesConverter;

import br.com.collegesmaster.model.security.Role;
import br.com.collegesmaster.view.converters.generics.GenericModelConverter;

@FacesConverter("roleConverter")
public class RoleConverter extends GenericModelConverter<Role> {
	
}