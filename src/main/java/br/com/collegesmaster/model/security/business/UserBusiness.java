package br.com.collegesmaster.model.security.business;

import br.com.collegesmaster.model.generics.GenericCRUD;
import br.com.collegesmaster.model.security.impl.UserImpl;

public interface UserBusiness extends GenericCRUD<UserImpl> {

	UserImpl findById(final Integer id);
	
	Boolean existsCpf(String cpf);
	
	Boolean existsEmail(String email);
	
	Boolean existsUsername(String username);
	
	UserImpl findByUsername(String username);
}
