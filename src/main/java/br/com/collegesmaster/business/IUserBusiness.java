package br.com.collegesmaster.business;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.imp.User;

public interface IUserBusiness extends IBusiness<IUser, User> {
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	IUser login(String username, String password);

}
