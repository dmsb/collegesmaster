package br.com.collegesmaster.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import br.com.collegesmaster.business.impl.InstituteBusiness;
import br.com.collegesmaster.business.impl.RoleBusiness;
import br.com.collegesmaster.business.impl.UserBusiness;

public class RestApplication extends Application {
	
	@Override
	public Set<Class<?>> getClasses() {
		
		final Set<Class<?>> restServices =  new HashSet<>();
		restServices.add(UserBusiness.class);
		restServices.add(InstituteBusiness.class);
		restServices.add(RoleBusiness.class);
		
		return restServices;
	}
}
