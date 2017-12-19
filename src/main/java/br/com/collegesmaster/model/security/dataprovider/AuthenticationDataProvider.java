package br.com.collegesmaster.model.security.dataprovider;

import java.util.List;

import javax.security.auth.login.LoginException;

import br.com.collegesmaster.model.user.impl.RoleImpl;

public interface AuthenticationDataProvider {

	List<RoleImpl> findUserRoles(final String username) throws LoginException;

	String findUserPassword(final String username) throws LoginException;

	String findUserSalt(final String username) throws LoginException;
	
}
