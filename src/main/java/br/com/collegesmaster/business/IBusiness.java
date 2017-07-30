package br.com.collegesmaster.business;

import static javax.ejb.TransactionAttributeType.NOT_SUPPORTED;
import static javax.ejb.TransactionAttributeType.REQUIRED;

import java.util.List;

import javax.ejb.TransactionAttribute;

import br.com.collegesmaster.model.IModel;
import br.com.collegesmaster.model.impl.Model;

public interface IBusiness <I extends IModel, C extends Model> {
	
	@TransactionAttribute(REQUIRED)
	public void save(I imodel);
	
	@TransactionAttribute(REQUIRED)
	public I update(I imodel) ;
	 
	@TransactionAttribute(REQUIRED)
	public void remove(I imodel);
	 
	@TransactionAttribute(NOT_SUPPORTED)
	public I findById(Integer id);
	
	@TransactionAttribute(REQUIRED)
	public List<C> findAllEnabledRolesToClients();

}