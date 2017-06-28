package br.com.collegesmaster.business;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.imp.User;

public interface IUserBusiness extends IBusiness<IUser> {
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	IUser login(String usernameToBeComparated, String passwordToBeComparated);

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	Boolean existsCpf(String cpfToBeComparated);

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	Boolean existsEmail(final String emailToBeComparated);

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	Boolean existsUsername(final String usernameToBeComparated);
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<User> findAll();

}
