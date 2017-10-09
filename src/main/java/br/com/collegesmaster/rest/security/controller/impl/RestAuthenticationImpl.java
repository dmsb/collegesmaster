package br.com.collegesmaster.rest.security.controller.impl;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.com.collegesmaster.rest.security.business.AuthenticationBusiness;
import br.com.collegesmaster.rest.security.controller.RestAuthentication;
import br.com.collegesmaster.security.model.Credentials;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class RestAuthenticationImpl implements RestAuthentication {

	private static final long serialVersionUID = -2870919955624591411L;
    
	
	@Inject
	private AuthenticationBusiness authenticationBusiness;
	
	@Override
	public Response login(@Context UriInfo uriInfo,
			@Context final HttpHeaders httpHeaders, final Credentials credentials) {

		try {
			
			authenticationBusiness.authenticate(credentials);

			final String absolutePath = uriInfo.getAbsolutePath().toString();
			final Date requestDate = httpHeaders.getDate();
			final String token = issueToken(credentials.getUsername(), absolutePath, requestDate);

			return Response.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();

		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	private String issueToken(final String username, final String absolutePath, final Date requestDate) 
			throws NoSuchAlgorithmException {
		
		final KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
		
        final Key key = keyGenerator.generateKey();
        final String jwtToken = Jwts.builder()
                .setSubject(username)
                .setIssuer(absolutePath)
                .setIssuedAt(requestDate)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return jwtToken;
    }

}
