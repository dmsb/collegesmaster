package br.com.collegesmaster.model.business;

import java.util.List;

import javax.ws.rs.core.UriInfo;

import br.com.collegesmaster.model.entities.model.Model;

public interface BasicCrudBusiness <M extends Model> {
	
	public Boolean create(M imodel);
	
	public M update(M imodel) ;

	public Boolean remove(M imodel);
	
	public M findById(Integer id);
	
	List<M> findAllByPredicates(UriInfo requestInfo);
	
	public List<M> findAll();
	
}