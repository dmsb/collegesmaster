package br.com.collegesmaster.business.imp;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

public abstract class Business {
	
	protected EntityManager entityManager;
	protected CriteriaBuilder criteriaBuilder;
    protected StringBuilder queryBuilder;   
    protected final static Logger logger = Logger.getGlobal();
    
    
    
    
}
