package br.com.collegesmaster.business.imp;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Subquery;

import br.com.collegesmaster.model.impl.User;

public abstract class GenericBusiness {
	
	@PersistenceContext(unitName = "collegesmasterPU")
	protected EntityManager entityManager;
	
	protected CriteriaBuilder builder;
    protected StringBuilder queryBuilder;   
    protected final static Logger logger = Logger.getGlobal();
    
    @PostConstruct
	protected void init() {
    	builder = entityManager.getCriteriaBuilder();    	
    }
    
	protected Boolean executeExists(final CriteriaQuery<Boolean> query, final Subquery<User> subquery,
			final Predicate...predicates) {
		
		subquery.where(predicates);
		final Predicate exists = builder.exists(subquery);
		query.where(exists);
		
		final TypedQuery<Boolean> typedQuery = entityManager.createQuery(query);		
		
		if(TRUE.equals(singleResult(typedQuery))) {
			return TRUE;	
		} else {
			return FALSE;
		}
	}

	protected CriteriaQuery<Boolean> buildBooleanReturnQuery(final Class<?> classz) {
		final CriteriaQuery<Boolean> query = builder.createQuery(Boolean.class);		
		query.from(classz);
		query.select(builder.literal(TRUE)).distinct(true);
		return query;
	}
	
	@SuppressWarnings("rawtypes")
	protected Object singleResult(final TypedQuery typedQuery) {
		try {
			return typedQuery.getSingleResult();
		} catch (NoResultException e) {
			logger.log(Level.INFO, "No results", e);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Fail to execute query!", e);
		}
		return null;
	}
}
