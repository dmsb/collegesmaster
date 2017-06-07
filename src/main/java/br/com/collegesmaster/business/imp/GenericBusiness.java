package br.com.collegesmaster.business.imp;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

public abstract class GenericBusiness {
	
	@PersistenceContext(unitName = "collegesmasterPU")
	protected EntityManager entityManager;
	
	protected CriteriaBuilder criteriaBuilder;
    protected StringBuilder queryBuilder;   
    protected final static Logger logger = Logger.getGlobal();

	public void init() {
    	criteriaBuilder = entityManager.getCriteriaBuilder();
    }

	public void cleanup() {
    	if(entityManager.isOpen()) {
    		entityManager.close();
    	}
    }
}
