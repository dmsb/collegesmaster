package br.com.collegesmaster.business.imp;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

@Stateless
public class Business {
	
	protected static EntityManager entityManager;
	protected static CriteriaBuilder criteriaBuilder;
    protected static StringBuilder queryBuilder;   
    protected final static Logger logger = Logger.getGlobal();
}
