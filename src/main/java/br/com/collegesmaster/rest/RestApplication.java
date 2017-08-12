package br.com.collegesmaster.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import br.com.collegesmaster.business.impl.ChallengeBusinessImpl;
import br.com.collegesmaster.business.impl.ChallengeResponseBusinessImpl;
import br.com.collegesmaster.business.impl.CourseBusinessImpl;
import br.com.collegesmaster.business.impl.DisciplineBusinessImpl;
import br.com.collegesmaster.business.impl.InstituteBusinessImpl;
import br.com.collegesmaster.business.impl.RoleBusinessImpl;
import br.com.collegesmaster.business.impl.UserBusinessImpl;
import br.com.collegesmaster.rest.config.JacksonConfig;

public class RestApplication extends Application {
	
	@Override
	public Set<Class<?>> getClasses() {
		
		final Set<Class<?>> restServices =  new HashSet<>();
		restServices.add(JacksonConfig.class);
		
		restServices.add(UserBusinessImpl.class);
		restServices.add(InstituteBusinessImpl.class);
		restServices.add(RoleBusinessImpl.class);
		restServices.add(ChallengeBusinessImpl.class);
		restServices.add(ChallengeResponseBusinessImpl.class);
		restServices.add(CourseBusinessImpl.class);
		restServices.add(DisciplineBusinessImpl.class);
		
		return restServices;
	}
}
