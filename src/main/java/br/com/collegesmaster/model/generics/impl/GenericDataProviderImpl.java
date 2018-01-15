package br.com.collegesmaster.model.generics.impl;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.logging.Logger;

import br.com.collegesmaster.model.generics.GenericDataProvider;
import br.com.collegesmaster.model.model.impl.ModelImpl;
import br.com.collegesmaster.qualifiers.UserDatabase;

@Stateless
@TransactionManagement(CONTAINER)
@PermitAll
@SecurityDomain("collegesmasterSecurityDomain")
public class GenericDataProviderImpl<T extends ModelImpl> implements GenericDataProvider<T> {
	
	@Inject
	private Logger LOGGER;
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean create(T model) {
		try {
			em.persist(model);
			return TRUE;
		} catch (EntityExistsException | IllegalArgumentException | 
				TransactionRequiredException e) {
			LOGGER.error(e);
			return FALSE;
		}
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public T update(T model) {
		try {
			return em.merge(model);
		} catch (IllegalArgumentException | TransactionRequiredException e) {
			LOGGER.error(e);
			return null;
		}
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean remove(T model) {
		try {
			em.remove(model);
			return TRUE;
		} catch (IllegalArgumentException | TransactionRequiredException e) {
			LOGGER.error(e);
			return FALSE;
		}
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public T findById(final Class<T> modelClass, final Integer id) {
		try {
			return em.find(modelClass, id);
		} catch (IllegalArgumentException e) {
			LOGGER.error(e);
			return null;
		}
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public List<T> findAll(Class<T> modelType) {
		final CriteriaQuery<T> criteriaQuery = cb.createQuery(modelType);		
		final TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);
		final List<T> result = typedQuery.getResultList();
		return result;
	}

}
