package br.com.collegesmaster.business.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

import br.com.collegesmaster.annotation.qualifier.UserDatabase;

@ApplicationScoped
public class PersistenceFactory {

	@PersistenceContext(name = "entityManagerJndi", unitName = "collegesmasterPU")
	@Produces
	@UserDatabase
	private EntityManager entityManager;
	
	@Produces
	public CriteriaBuilder criteriaBulder() {
		return entityManager.getCriteriaBuilder();
	}

	public void close(@Disposes @UserDatabase EntityManager em) {
		em.close();
	}
}
