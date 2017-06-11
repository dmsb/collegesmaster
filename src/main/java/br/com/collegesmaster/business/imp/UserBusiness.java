package br.com.collegesmaster.business.imp;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.collegesmaster.business.IUserBusiness;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.imp.User;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UserBusiness extends GenericBusiness implements IUserBusiness {
	
	@Override	
	public void persist(final IUser user) {		
		entityManager.persist(user);
		
	}

	@Override
	public void merge(final IUser user) {
		entityManager.merge(user);
		
	}

	@Override
	public void remove(final IUser user) {
		entityManager.remove(user);
		
	}

	@Override
	public IUser findById(final Integer id, final Class<User> modelClass) {
		return entityManager.find(modelClass, id);
	}

	@Override
	public List<User> getList() {
		final CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		criteriaQuery.from(User.class);
		final TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
		final List<User> result = typedQuery.getResultList(); 
		
		return result;
	}
	
}
