package br.com.collegesmaster.rest.security.controller.impl;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import br.com.collegesmaster.model.user.User;
import br.com.collegesmaster.model.user.business.UserBusiness;
import br.com.collegesmaster.rest.security.controller.AuthenticationController;

@RequestScoped
public class AuthenticationControllerImpl implements AuthenticationController {
	
	@Inject
	private Logger LOGGER;
	
	@Inject
	private transient UserBusiness userBusiness;
	
	@Override
	public Response jaasAuthentication(final HttpServletRequest authenticationRequest, 
			final String username, final String password) {
		
		User user = null;
		
		try {
			logoutPrincipalIfExists(authenticationRequest);
			authenticationRequest.login(username, password);
			user = userBusiness.findByUsername(username);
			LOGGER.info("Login success!");
			
		} catch (ServletException e) {
			LOGGER.warn("User not authenticated!", e);
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
		return Response.ok().entity(user).build();
	}
	
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
