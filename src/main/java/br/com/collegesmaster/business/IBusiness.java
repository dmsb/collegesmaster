package br.com.collegesmaster.business;

import java.util.List;

import br.com.collegesmaster.model.IModel;

public interface IBusiness <T extends IModel> {
	
	public void persist(T imodel);
	
	public void merge(T imodel) ;
	
	public void remove(T imodel);
	
	public T findById(Integer id, Class<T> modelClass);
	
	public List<T> getList();
}