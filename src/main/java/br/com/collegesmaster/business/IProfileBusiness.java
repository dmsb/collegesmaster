package br.com.collegesmaster.business;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.collegesmaster.model.IProfile;
import br.com.collegesmaster.model.imp.Profile;

public interface IProfileBusiness extends IBusiness<IProfile> {
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Profile> findAll();
}
