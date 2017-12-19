package br.com.collegesmaster.model.user.dataprovider;

import br.com.collegesmaster.model.generics.BasicCrud;
import br.com.collegesmaster.model.user.impl.UserImpl;

public interface UserDataProvider extends BasicCrud<UserImpl> {

	Boolean existsCpf(String cpf);
	
	Boolean existsEmail(String email);
	
	Boolean existsUsername(String username);
	
	UserImpl findByUsername(String username);
	
}
