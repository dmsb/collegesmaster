package br.com.collegesmaster.model.security.dataprovider;

import br.com.collegesmaster.model.security.impl.UserImpl;

public interface UserDataProvider {

	Boolean existsCpf(String cpf);
	
	Boolean existsEmail(String email);
	
	Boolean existsUsername(String username);
	
	UserImpl findByUsername(String username);
	
}
