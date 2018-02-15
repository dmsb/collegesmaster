package br.com.collegesmaster.model.security.business.impl;

import static javax.ejb.TransactionAttributeType.NEVER;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.Collection;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.logging.Logger;

import br.com.collegesmaster.model.security.Credentials;
import br.com.collegesmaster.model.security.business.AuthenticationBusiness;
import br.com.collegesmaster.model.security.dataprovider.AuthenticationDataProvider;
import br.com.collegesmaster.model.security.impl.RoleImpl;
import br.com.collegesmaster.view.enums.Page;

@Stateless
@TransactionManagement(CONTAINER)
@SecurityDomain("collegesmasterSecurityDomain")
public class AuthenticationBusinessImpl implements AuthenticationBusiness {
	
	@Inject
	private Logger LOGGER;

	@Inject
	private AuthenticationDataProvider authenticationDataProvider;
	
	@TransactionAttribute(NEVER)
	@PermitAll
	@Override
	public String processLoginServletRequest(final Credentials credentials, 
			final HttpServletRequest loginRequest) {
		try {
			checkIfExistsALoggedUser(loginRequest);
			loginRequest.login(credentials.getUsername(), credentials.getPassword());
			if (loginRequest.getUserPrincipal() != null) {
				if (loginRequest.isUserInRole("PROFESSOR")) {
					return Page.getPageByCode("create_challenge");
				} else if (loginRequest.isUserInRole("STUDENT")) {
					return Page.getPageByCode("reply_challenges");
				}
			}
		} catch (ServletException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	@TransactionAttribute(NEVER)
	@PermitAll
	@Override
	public void checkIfExistsALoggedUser(final HttpServletRequest loginRequest) throws ServletException {
		if (loginRequest.getUserPrincipal() != null) {
			loginRequest.logout();
		}
	}
	
	@TransactionAttribute(REQUIRED)
	@PermitAll
	@Override
	public String findUserSalt(final String username) throws LoginException {
		return authenticationDataProvider.findUserSalt(username);
	}
	
	@TransactionAttribute(REQUIRED)
	@PermitAll
	@Override
	public String findUserPassword(final String username) throws LoginException {
		return authenticationDataProvider.findUserPassword(username);
	}
	
	@TransactionAttribute(REQUIRED)
	@PermitAll
	@Override
	public Collection<RoleImpl> findUserRoles(final String username) throws LoginException {
		return authenticationDataProvider.findUserRoles(username);
	}	
}
