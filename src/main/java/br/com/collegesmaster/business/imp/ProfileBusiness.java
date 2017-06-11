package br.com.collegesmaster.business.imp;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.collegesmaster.business.IProfileBusiness;
import br.com.collegesmaster.model.IProfile;
import br.com.collegesmaster.model.imp.Profile;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProfileBusiness extends GenericBusiness implements IProfileBusiness {
	
	@Override
	public void persist(IProfile profile) {
		entityManager.persist(profile);

	}

	@Override
	public void merge(IProfile profile) {
		entityManager.merge(profile);
	}

	@Override
	public void remove(IProfile profile) {
		entityManager.remove(profile);
	}

	@Override
	public List<Profile> getList() {		
		final CriteriaQuery<Profile> criteriaQuery = criteriaBuilder.createQuery(Profile.class);
		criteriaQuery.from(Profile.class);
		final TypedQuery<Profile> typedQuery = entityManager.createQuery(criteriaQuery);		
		final List<Profile> result = typedQuery.getResultList(); 
		
		return result;
	}

	@Override
	public IProfile findById(Integer id, Class<Profile> modelClass) {
		return entityManager.find(modelClass, id);
	}
}
