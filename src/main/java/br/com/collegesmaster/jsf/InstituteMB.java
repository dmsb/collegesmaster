package br.com.collegesmaster.jsf;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import br.com.collegesmaster.business.IInstituteBusiness;
import br.com.collegesmaster.model.IInstitute;

@ManagedBean(name = "instituteMB")
public class InstituteMB {
	
	@EJB
	private IInstituteBusiness instituteBusiness;
	
	private IInstitute institute;
	private List<IInstitute> institutes;
	
	public List<IInstitute> buildInstituteList() {
		return instituteBusiness.getList();
	}
	
	public void persistInstitute() {
		instituteBusiness.persist(institute);
	}

	public IInstitute getInstitute() {
		return institute;
	}

	public void setInstitute(IInstitute institute) {
		this.institute = institute;
	}

	public List<IInstitute> getInstitutes() {
		return institutes;
	}

	public void setInstitutes(List<IInstitute> institutes) {
		this.institutes = institutes;
	}
}
