package br.com.collegesmaster.business.imp;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.collegesmaster.business.IProfileBusiness;
import br.com.collegesmaster.model.IProfile;
import br.com.collegesmaster.model.impl.Profile;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProfileBusiness extends GenericBusiness implements IProfileBusiness {
	
	@Override
	public void save(IProfile profile) {
		entityManager.persist(profile);

	}

	@Override
	public IProfile update(IProfile profile) {
		return entityManager.merge(profile);
	}

	@Override
	public void remove(IProfile profile) {
		entityManager.remove(profile);
	}
	
	@Override
	public IProfile findById(Integer id) {
		return entityManager.find(Profile.class, id);
	}
	
	@Override
	public List<Profile> findAll() {		
		final CriteriaQuery<Profile> criteriaQuery = builder.createQuery(Profile.class);
		criteriaQuery.from(Profile.class);
		final TypedQuery<Profile> typedQuery = entityManager.createQuery(criteriaQuery);		
		final List<Profile> result = typedQuery.getResultList(); 
		
		return result;
	}

}
