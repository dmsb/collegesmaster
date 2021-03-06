package br.com.collegesmaster.model.security.business;

import java.util.Collection;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import br.com.collegesmaster.model.security.Credentials;
import br.com.collegesmaster.model.security.impl.RoleImpl;

public interface AuthenticationBusiness {

	Collection<RoleImpl> findUserRoles(final String username) throws LoginException;

	String findUserPassword(final String username) throws LoginException;

	String findUserSalt(final String username) throws LoginException;

	void checkIfExistsALoggedUser(final HttpServletRequest loginRequest) throws ServletException;

	String processLoginServletRequest(final Credentials credentials, final HttpServletRequest loginRequest);

}
