package br.com.collegesmaster.business;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import javax.ejb.TransactionAttribute;

import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.impl.User;

public interface IUserBusiness extends IBusiness<IUser, User> {

	@TransactionAttribute(REQUIRED)
	Boolean existsCpf(String cpfToBeComparated);

	@TransactionAttribute(REQUIRED)
	Boolean existsEmail(final String emailToBeComparated);

	@TransactionAttribute(REQUIRED)
	Boolean existsUsername(final String usernameToBeComparated);
	
	@TransactionAttribute(REQUIRED)
	IUser findByUsername(String username);
}
