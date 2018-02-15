package br.com.collegesmaster.model.security.dataprovider;

import java.util.Collection;

import javax.security.auth.login.LoginException;

import br.com.collegesmaster.model.security.impl.RoleImpl;

public interface AuthenticationDataProvider {

	Collection<RoleImpl> findUserRoles(final String username) throws LoginException;

	String findUserPassword(final String username) throws LoginException;

	String findUserSalt(final String username) throws LoginException;
	
}
