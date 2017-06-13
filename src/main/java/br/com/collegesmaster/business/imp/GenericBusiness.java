package br.com.collegesmaster.business.imp;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

public abstract class GenericBusiness {
	
	@PersistenceContext(unitName = "collegesmasterPU")
	protected EntityManager entityManager;
	
	protected CriteriaBuilder criteriaBuilder;
    protected StringBuilder queryBuilder;   
    protected final static Logger LOGGER = Logger.getGlobal();
    
    @PostConstruct
	public void init() {
    	criteriaBuilder = entityManager.getCriteriaBuilder();    	
    }
}
