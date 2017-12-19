package br.com.collegesmaster.model.generics.producers;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static javax.ejb.TransactionManagementType.CONTAINER;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.jboss.logging.Logger;

import br.com.collegesmaster.model.model.Model;
import br.com.collegesmaster.qualifiers.UserDatabase;

@Stateless
@TransactionManagement(CONTAINER)
public class BooleanReponseFactory<T extends Model> {
	
	@Inject
	private Logger LOGGER;
	
	@Inject
	private CriteriaBuilder cb;
	
	@Inject
	@UserDatabase
	private EntityManager em;
	
	private CriteriaQuery<Boolean> booleanQuery;
	
	private Subquery<T> subquery;
	
	private Root<T> root;
	
	public Root<T> build(final Class<T> targetClass) {
		booleanQuery = cb.createQuery(Boolean.class);
		booleanQuery.from(targetClass);
		booleanQuery.select(cb.literal(TRUE)).distinct(true);
		subquery = booleanQuery.subquery(targetClass);
		root = subquery.from(targetClass);
		subquery.select(root);
		return root;
	}
	
	public BooleanReponseFactory<T> where(final Predicate... predicates) {
		subquery.where(predicates);
		final Predicate exists = cb.exists(subquery);
		booleanQuery.where(exists);
		return this;
	}
	
	public Boolean execute() {

		final TypedQuery<Boolean> typedQuery = em.createQuery(booleanQuery);

		if (TRUE.equals(singleResult(typedQuery))) {
			return TRUE;
		} else {
			return FALSE;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Object singleResult(final TypedQuery typedQuery) {
		try {
			return typedQuery.getSingleResult();
		} catch (NoResultException e) {
			LOGGER.info("No results");
		}
		return null;
	}
}
