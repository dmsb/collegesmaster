package br.com.collegesmaster.business;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.collegesmaster.model.IRole;
import br.com.collegesmaster.model.impl.Role;

public interface IProfileBusiness extends IBusiness<IRole> {
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Role> findAll();
}
