package br.com.collegesmaster.model.generics;

import br.com.collegesmaster.model.model.impl.ModelImpl;

public interface GenericDataProvider <T extends ModelImpl> extends GenericCRUD<T> {
	
}
