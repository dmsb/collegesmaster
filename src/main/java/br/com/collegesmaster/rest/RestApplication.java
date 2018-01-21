package br.com.collegesmaster.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import br.com.collegesmaster.rest.config.JacksonConfig;
import br.com.collegesmaster.rest.controllers.impl.AuthenticationControllerImpl;
import br.com.collegesmaster.rest.controllers.impl.InstituteControllerImpl;
import br.com.collegesmaster.rest.controllers.impl.RoleControllerImpl;
import br.com.collegesmaster.rest.controllers.impl.UserControllerImpl;
import br.com.collegesmaster.rest.handlers.RestExceptionHandler;

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
