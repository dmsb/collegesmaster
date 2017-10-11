package br.com.collegesmaster.rest.security.controller.impl;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.logging.Logger;

import br.com.collegesmaster.business.UserBusiness;
import br.com.collegesmaster.model.impl.UserImpl;
import br.com.collegesmaster.rest.security.controller.AuthenticationController;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@SecurityDomain("collegesmasterSecurityDomain")
public class AuthenticationControllerImpl implements AuthenticationController {

	private static final long serialVersionUID = -2870919955624591411L;
	
	@Inject
	private Logger LOGGER;
	
	@Inject
	private transient UserBusiness userBusiness;
	
	private UserImpl loggedUser;
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public Response jaasAuthentication(final HttpServletRequest authenticationRequest, 
			final String username, final String password) {
		
		try {
			logoutPrincipalIfExists(authenticationRequest);
			authenticationRequest.login(username, password);
			loggedUser = userBusiness.findByUsername(username);
			LOGGER.info("Login success!");
			
		} catch (ServletException e) {
			LOGGER.warn("User not authenticated!", e);
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
		return Response.ok().entity(loggedUser).build();
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public Response jaasLogout(final HttpServletRequest logoutRequest) {
		
		try {
			logoutPrincipalIfExists(logoutRequest);
		} catch (ServletException e) {
			LOGGER.warn("Error in user logout!!", e);
			return Response.status(Status.NOT_ACCEPTABLE).build();
		}
		
		return Response.ok().build();
	}

	private void logoutPrincipalIfExists(final HttpServletRequest loginRequest) throws ServletException {
		if (loginRequest.getUserPrincipal() != null) {
			loginRequest.logout();
		} else {
			LOGGER.info("This user isn't logged");
		}
	}

}
