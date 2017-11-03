package br.com.collegesmaster.factories;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.jboss.logging.Logger;

@ApplicationScoped
public class LoggerFactory {

	@Produces
	public Logger getLogger(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getSimpleName());
	}
	
}
