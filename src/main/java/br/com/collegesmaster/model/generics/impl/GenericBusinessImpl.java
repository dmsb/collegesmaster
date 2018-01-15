package br.com.collegesmaster.model.generics.impl;

import static java.lang.Boolean.FALSE;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import br.com.collegesmaster.model.generics.GenericBusiness;
import br.com.collegesmaster.model.generics.GenericDataProvider;
import br.com.collegesmaster.model.model.impl.ModelImpl;

@Dependent
public abstract class GenericBusinessImpl<T extends ModelImpl> implements GenericBusiness<T> {

	@Inject
	private Logger LOGGER;
	
	@EJB
	private GenericDataProvider<T> genericDataProvider;
	
	@Override
	public Boolean create(final T user) {
		if(user != null && user.isNew()) {
			return genericDataProvider.create(user);
		} else {
			LOGGER.warn("Entity not persisted, invalid arguments");
			return FALSE;			
		}
	}

	@Override
	public T update(final T user) {
		if(user != null && Boolean.FALSE.equals(user.isNew())) {
			return genericDataProvider.update(user);
		} else {
			LOGGER.warn("Entity not persisted, invalid arguments");
			return null;
		}
	}

	@Override
	public Boolean remove(final T user) {
		if(user != null && Boolean.FALSE.equals(user.isNew())) {
			return genericDataProvider.remove(user);
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

	@Override
	public List<T> findAll(Class<T> modelType) {
		if(modelType != null) {
			return genericDataProvider.findAll(modelType);
		} else {
			LOGGER.warn("Cannot find entity, modelType is null");
			return null;
		}
	}
}
