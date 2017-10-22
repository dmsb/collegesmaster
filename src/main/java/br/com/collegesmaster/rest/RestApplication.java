package br.com.collegesmaster.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import br.com.collegesmaster.rest.config.JacksonConfig;
import br.com.collegesmaster.rest.controller.impl.InstituteControllerImpl;
import br.com.collegesmaster.rest.controller.impl.RoleControllerImpl;
import br.com.collegesmaster.rest.controller.impl.UserControllerImpl;
import br.com.collegesmaster.rest.handler.RestExceptionHandler;
import br.com.collegesmaster.rest.security.controller.impl.AuthenticationControllerImpl;

public class RestApplication extends Application {
	
	@Override
	public Set<Class<?>> getClasses() {
		
		final Set<Class<?>> restServices =  new HashSet<>();
		restServices.add(RestExceptionHandler.class);
		restServices.add(JacksonConfig.class);
		
		restServices.add(InstituteControllerImpl.class);
		restServices.add(RoleControllerImpl.class);
		restServices.add(UserControllerImpl.class);
		restServices.add(AuthenticationControllerImpl.class);
		
		
		return restServices;
	}

	
}
