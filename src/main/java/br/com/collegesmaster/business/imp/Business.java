package br.com.collegesmaster.business.imp;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;

@Stateless
public class Business {
	
	@PersistenceUnit(unitName = "collegesmasterPU")
	protected static EntityManagerFactory entityManagerFactory;	
	
	@PersistenceContext(unitName = "collegesmasterPU")
	protected static EntityManager entityManager;
	protected static CriteriaBuilder criteriaBuilder;
	
    protected static StringBuilder queryBuilder;   
    protected final static Logger logger = Logger.getGlobal();

	@PostConstruct
	public void init() {
    	entityManager = entityManagerFactory.createEntityManager();
    	criteriaBuilder = entityManager.getCriteriaBuilder();
    }

	@PreDestroy
	public void cleanup() {
    	if(entityManager.isOpen()) {
    		entityManager.close();
    	}
    }
    
}
