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
	protected static EntityManagerFactory emf;	
    protected static EntityManager em;
	
    protected static StringBuilder queryBuilder;   
    protected final static Logger logger = Logger.getGlobal();
    
    @Override
	@PostConstruct
	public void init() {
    	em = emf.createEntityManager();
    }
    
    @Override
	@PreDestroy
	public void cleanup() {
    	if(em.isOpen()) {
    		em.close();
    	}
    }	
    
}
