package br.com.collegesmaster.model.generics.impl;

import static java.lang.Boolean.FALSE;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import br.com.collegesmaster.model.generics.GenericCRUD;
import br.com.collegesmaster.model.generics.GenericDataProvider;
import br.com.collegesmaster.model.model.Model;

@Dependent
public abstract class GenericBusinessImpl<T extends Model> implements GenericCRUD<T> {

	@Inject
	private Logger LOGGER;
	
	@Inject
	private GenericDataProvider<T> genericDataProvider;
	
	@Override
	public abstract Boolean create(final T model);
	
	@Override
	public abstract T update(final T model);
	
	@Override
	public abstract Boolean remove(final T model);
	
	public Boolean genericCreate(final T model) {
		if(model != null && model.isNew()) {
			return genericDataProvider.create(model);
		} else {
			LOGGER.warn("Entity not persisted, invalid arguments");
			return FALSE;			
		}
	}
	
	public T genericUpdate(final T model) {
		if(model != null && Boolean.FALSE.equals(model.isNew())) {
			return genericDataProvider.update(model);
		} else {
			LOGGER.warn("Entity not persisted, invalid arguments");
			return null;
		}
	}

	public Boolean genericRemove(final T model) {
		if(model != null && Boolean.FALSE.equals(model.isNew())) {
			return genericDataProvider.remove(model);
		} else {
			LOGGER.warn("Entity not removed, invalid arguments");
			return FALSE;
		}
	}
	
	@Override
	public T findById(final Class<T> modelClass, final Integer id) {
		if(id != null) {
			return genericDataProvider.findById(modelClass, id);			
		} else {
			LOGGER.warn("Cannot find entity, invalid arguments");
			return null;
		}
	}
}
