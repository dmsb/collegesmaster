package br.com.collegesmaster.business.imp;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;

public class Business {
	
	@PersistenceUnit(unitName = "collegesmasterPU")
	protected static EntityManagerFactory entityManagerFactory;	
    
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