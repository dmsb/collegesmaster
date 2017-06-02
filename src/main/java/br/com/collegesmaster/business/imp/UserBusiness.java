package br.com.collegesmaster.business.imp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.collections.CollectionUtils;

import br.com.collegesmaster.business.IUserBusiness;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.imp.User;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UserBusiness extends Business implements IUserBusiness {

	@PersistenceUnit(unitName = "collegesmasterPU")
	protected EntityManagerFactory entityManagerFactory;
	
	@Override
	@PostConstruct
	public void init() {
    	entityManager = entityManagerFactory.createEntityManager();
    	criteriaBuilder = entityManager.getCriteriaBuilder();
    }
	
	@Override
	@PreDestroy
	public void cleanup() {
    	if(entityManager.isOpen()) {
    		entityManager.close();
    	}
    }
	
	@Override
	public void persist(IUser user) {
		entityManager.persist(user);
		
	}

	@Override
	public void merge(IUser user) {
		entityManager.merge(user);
		
	}

	@Override
	public void remove(IUser user) {
		entityManager.remove(user);
		
	}

	@Override
	public IUser findById(Integer id, Class<IUser> modelClass) {
		return entityManager.find(modelClass, id);
	}

	@Override
	public List<IUser> getList() {
		final CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		final TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
		final List<User> result = typedQuery.getResultList(); 
		
		if(CollectionUtils.isEmpty(result)) {
			return null;
		} else {
			final List<IUser> users = new ArrayList<IUser>();
			result.forEach(user -> users.add(user));
			return users;
		}
	}

}
