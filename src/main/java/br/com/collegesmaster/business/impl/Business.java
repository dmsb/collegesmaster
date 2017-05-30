package br.com.collegesmaster.business.impl;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import br.com.collegesmaster.business.IBusiness;

@Stateless
public class Business implements IBusiness {
	
	@PersistenceUnit(unitName = "collegesmasterPU")
	protected static EntityManagerFactory entityManagerFactory;	
    protected static EntityManager entityManager;
	
    protected static StringBuilder queryBuilder;   
    protected final static Logger logger = Logger.getGlobal();
    
    @Override
	@PostConstruct
	public void init() {
    	entityManager = entityManagerFactory.createEntityManager();
    }
    
    @Override
	@PreDestroy
	public void cleanup() {
    	if(entityManager.isOpen()) {
    		entityManager.close();
    	}
    }	
    
}
