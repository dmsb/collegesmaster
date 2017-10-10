package br.com.collegesmaster.rest.security.controller.impl;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.logging.Logger;

import br.com.collegesmaster.rest.security.business.AuthenticationBusiness;
import br.com.collegesmaster.rest.security.controller.AuthenticationController;
import br.com.collegesmaster.security.model.Credentials;
import br.com.collegesmaster.security.model.impl.CredentialsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@SecurityDomain("collegesmasterSecurityDomain")
public class AuthenticationControllerImpl implements AuthenticationController {

	private static final long serialVersionUID = -2870919955624591411L;
	
	@Inject
	private Key securedKey;
	
	@Inject
	private Logger LOGGER;
	
	@EJB
	private AuthenticationBusiness authenticationBusiness;
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public Response restAuthentication(@Context UriInfo uriInfo,
	    	@QueryParam("username")String username, @QueryParam("password")String password) {

		try {
			
			final Credentials credentials = new CredentialsImpl();
			credentials.setUsername(username);
			credentials.setPassword(password);
			
			authenticationBusiness.authenticate(credentials);
			
			final String absolutePath = uriInfo.getAbsolutePath().toString();
			final String token = issueToken(credentials.getUsername(), absolutePath);

			return Response.ok(credentials)
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
					.build();

		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	private String issueToken(final String username, final String absolutePath) 
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
        final String jwtToken = Jwts.builder()
        		.setSubject(username)
        		.signWith(SignatureAlgorithm.HS512, securedKey)
                .setIssuer(absolutePath)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(new Date().toInstant().plusSeconds(10)))
                .compact();
        return jwtToken;
    }

	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public Response jaasAuthentication(final HttpServletRequest authenticationRequest, 
			final String username, final String password) {
		
		try {
			logoutPrincipalIfExists(authenticationRequest);
			authenticationRequest.login(username, password);
			
		} catch (ServletException e) {
			LOGGER.warn("User not authenticated!", e);
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
		return Response.ok().build();
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
		}
	}

}
