package br.com.collegesmaster.model.security.dataprovider.impl;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jboss.ejb3.annotation.SecurityDomain;

import br.com.collegesmaster.model.security.dataprovider.RoleDataProvider;
import br.com.collegesmaster.model.security.impl.RoleImpl;
import br.com.collegesmaster.model.security.impl.RoleImpl_;
import br.com.collegesmaster.qualifiers.UserDatabase;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class RoleDataProviderImpl implements RoleDataProvider {
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;

	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public List<RoleImpl> findAllElegibleRoles() {
		final CriteriaQuery<RoleImpl> criteriaQuery = cb.createQuery(RoleImpl.class);
		Root<RoleImpl> rootRole = criteriaQuery.from(RoleImpl.class);

		final Predicate exceptAdmRole = cb.notEqual(rootRole.get(RoleImpl_.name), "ADMINISTRATOR");
		criteriaQuery.where(exceptAdmRole);
		
		final TypedQuery<RoleImpl> typedQuery = em.createQuery(criteriaQuery);
		final List<RoleImpl> result = typedQuery.getResultList();
		
		return result;
	}

	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public List<RoleImpl> findAllByPredicates(Map<String, Object> predicateMap) {

		final CriteriaQuery<RoleImpl> criteriaQuery = cb.createQuery(RoleImpl.class);		
		final Root<RoleImpl> instituteRoot = criteriaQuery.from(RoleImpl.class);
		
		final List<Predicate> predicates = new ArrayList<>();
		predicateMap.forEach((key, value) -> {
			predicates.add(cb.equal(instituteRoot.get(key), value));
		});

		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		final TypedQuery<RoleImpl> typedQuery = em.createQuery(criteriaQuery);		
		
		return typedQuery.getResultList();
	}
}
