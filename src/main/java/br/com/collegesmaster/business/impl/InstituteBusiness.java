package br.com.collegesmaster.business.impl;

import br.com.collegesmaster.business.IInstituteBusiness;
import br.com.collegesmaster.model.Institute;

public class InstituteBusiness extends Business implements IInstituteBusiness {

	@Override
	public void persistInstitute(Institute institute) {		
		entityManager.persist(institute);
	}
	
	@Override
	public void mergeInstitute(Institute institute) {
		entityManager.merge(institute);
	}
	
	@Override
	public void removeInstitute(Institute institute) {
		entityManager.remove(institute);
	}
}
