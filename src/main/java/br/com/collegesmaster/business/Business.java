package br.com.collegesmaster.business;

import static javax.ejb.TransactionAttributeType.NOT_SUPPORTED;
import static javax.ejb.TransactionAttributeType.REQUIRED;

import java.util.List;

import javax.ejb.TransactionAttribute;

import br.com.collegesmaster.model.Model;

public interface Business <M extends Model> {

	@TransactionAttribute(REQUIRED)
	public void create(M imodel);
	
	@TransactionAttribute(REQUIRED)
	public M update(M imodel) ;
	
	@TransactionAttribute(REQUIRED)
	public void remove(M imodel);
	
	@TransactionAttribute(NOT_SUPPORTED)
	public M findById(Integer id);
	
	@TransactionAttribute(REQUIRED)
	public List<M> findAll();

}