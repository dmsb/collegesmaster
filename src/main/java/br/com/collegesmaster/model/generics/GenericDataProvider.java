package br.com.collegesmaster.model.generics;

import java.util.List;

import br.com.collegesmaster.model.model.impl.ModelImpl;

public interface GenericDataProvider <T extends ModelImpl> {
	
	Boolean create(T model);
	
	T update(T model);

	Boolean remove(T model);
	
	T findById(Class<T> modelClass, Integer id);
	
	List<T> findAll(Class<T> modelType);
}
