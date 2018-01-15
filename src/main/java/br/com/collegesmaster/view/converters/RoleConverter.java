package br.com.collegesmaster.view.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.google.common.base.Strings;

import br.com.collegesmaster.model.security.Role;
import br.com.collegesmaster.model.security.impl.RoleImpl;

@FacesConverter(value = "roleConverter")
public class RoleConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String role) {
		if(!Strings.isNullOrEmpty(role)) {
			return (Role)component.getAttributes().get(role);
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object role) {
		
		if(role instanceof RoleImpl) {
			Role currentRole = (Role) role;
			if(currentRole.getId() != null) {
				component.getAttributes().put(currentRole.getId().toString(), currentRole);
				return currentRole.getId().toString();
			} else {
				return null;
			}
		} else {
			return null;	
		}
		
	}
}