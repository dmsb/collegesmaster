package br.com.collegesmaster.rest.security.controller.impl;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
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
@TransactionManagement(CONTAINER)
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
			final Boolean logoutSuccess = logoutPrincipalIfExists(logoutRequest);
			return returnLogoutResponse(logoutSuccess);
		} catch (ServletException e) {
			LOGGER.warn("Error in user logout!!", e);
			return Response.status(Status.NOT_ACCEPTABLE).build();
		}
	}

	private Response returnLogoutResponse(Boolean logoutSuccess) {
		if(logoutSuccess) {
			return Response.ok().build();
		} else {
			return Response.status(Status.NOT_MODIFIED).build();
		}
	}

	private Boolean logoutPrincipalIfExists(final HttpServletRequest loginRequest) throws ServletException {
		if (loginRequest.getUserPrincipal() != null) {
			loginRequest.logout();
			return TRUE;
		} else {
			LOGGER.info("This user isn't logged");
			return FALSE;
		}
	}

}
