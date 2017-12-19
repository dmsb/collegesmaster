package br.com.collegesmaster.model.generics;

import java.util.List;

import br.com.collegesmaster.model.model.Model;

public interface BasicCrud <M extends Model> {
	
	Boolean create(final M imodel);
	
	M update(final M imodel);

	Boolean remove(final M imodel);
	
	M findById(final Integer id);
	
	List<M> findAll();
	
}