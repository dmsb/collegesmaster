package br.com.collegesmaster.business;

import br.com.collegesmaster.model.impl.UserImpl;

public interface UserBusiness extends Business<UserImpl> {

	Boolean existsCpf(String cpfToBeComparated);

	Boolean existsEmail(String emailToBeComparated);

	Boolean existsUsername(String usernameToBeComparated);

	UserImpl findByUsername(String username);
}
