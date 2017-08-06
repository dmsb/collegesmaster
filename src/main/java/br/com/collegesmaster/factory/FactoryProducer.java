package br.com.collegesmaster.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

import org.jboss.logging.Logger;

import br.com.collegesmaster.annotation.qualifier.UserDatabase;

@ApplicationScoped
public class FactoryProducer {

	@PersistenceContext(unitName = "collegesmasterPU")
	@Produces
	@UserDatabase
	private EntityManager entityManager;
	
	@Produces
	public CriteriaBuilder criteriaBulder() {
		return entityManager.getCriteriaBuilder();
	}
	
	@Produces Logger getLogger(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getSimpleName());
	}

	public void close(@Disposes @UserDatabase EntityManager em) {
		em.close();
	}
}
