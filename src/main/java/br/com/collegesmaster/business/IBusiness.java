package br.com.collegesmaster.business;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.collegesmaster.model.IModel;

public interface IBusiness <T extends IModel, K> {
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void persist(T imodel);
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void merge(T imodel) ;
	 
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remove(T imodel);
	 
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public T findById(Integer id, Class<K> modelClass);
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<K> getList();
}