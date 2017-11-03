package br.com.collegesmaster.model.business;

import br.com.collegesmaster.model.entities.user.impl.UserImpl;

public interface UserBusiness extends BasicCrudBusiness<UserImpl> {

	Boolean existsCpf(String cpf);
	
	Boolean existsEmail(String email);
	
	Boolean existsUsername(String username);
	
	UserImpl findByUsername(String username);
}
