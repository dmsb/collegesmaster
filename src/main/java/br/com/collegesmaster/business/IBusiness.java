package br.com.collegesmaster.business;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.collegesmaster.model.IModel;

public interface IBusiness <T extends IModel> {
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void save(T imodel);
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public T update(T imodel) ;
	 
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remove(T imodel);
	 
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public T findById(Integer id);

}