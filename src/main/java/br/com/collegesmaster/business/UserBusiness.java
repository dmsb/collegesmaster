package br.com.collegesmaster.business;

import br.com.collegesmaster.model.impl.UserImpl;

public interface UserBusiness extends BasicCrudBusiness<UserImpl> {

	Boolean existsCpf(String cpf);
	
	Boolean existsEmail(String email);
	
	Boolean existsUsername(String username);
	
	UserImpl findByUsername(String username);
}
