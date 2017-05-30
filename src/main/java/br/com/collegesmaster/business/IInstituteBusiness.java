package br.com.collegesmaster.business;

import br.com.collegesmaster.model.Institute;

public interface IInstituteBusiness extends IBusiness {
	
	public void persistInstitute(Institute institute);

	public void mergeInstitute(Institute institute);
	
	public void removeInstitute(Institute institute);
}
