package br.com.collegesmaster.business;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.collegesmaster.model.IModel;
import br.com.collegesmaster.model.impl.Model;

public interface IBusiness <I extends IModel, C extends Model> {
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void save(I imodel);
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public I update(I imodel) ;
	 
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remove(I imodel);
	 
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public I findById(Integer id);
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<C> findAll();

}