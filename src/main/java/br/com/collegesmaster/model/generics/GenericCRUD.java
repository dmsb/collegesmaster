package br.com.collegesmaster.model.generics;

import br.com.collegesmaster.model.model.impl.ModelImpl;

public interface GenericCRUD <T extends ModelImpl> {
	
	Boolean create(T model);
	
	T update(T model);

	Boolean remove(T model);
	
	T findById(Class<T> modelClass, Integer id);
	
}
