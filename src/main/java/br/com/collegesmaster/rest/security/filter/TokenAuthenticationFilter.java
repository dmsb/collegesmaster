package br.com.collegesmaster.rest.security.filter;

import static javax.ws.rs.Priorities.AUTHENTICATION;

import java.io.IOException;
import java.security.Key;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import br.com.collegesmaster.annotation.qualifier.Secured;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

@Secured
@Provider
@Priority(AUTHENTICATION)
public class TokenAuthenticationFilter implements ContainerRequestFilter {
	
	private static final String AUTHENTICATION_SCHEME = "Bearer";
	
	@Inject
	private Key securedKey;
	
	@Inject
	private Logger LOGGER;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		final String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        final String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
        
        try {
            validateToken(token);
        } catch (Exception e) {
            abortWithUnauthorized(requestContext);
        }
	}
	
	private boolean isTokenBasedAuthentication(final String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                    .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(final ContainerRequestContext requestContext) {
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME)
                        .build());
    }

    private void validateToken(final String token) throws Exception {
    	try {
    		Jwts.parser().setSigningKey(securedKey).parseClaimsJws(token);    		
    	} catch (SignatureException e) {
    		LOGGER.error("Error in key validation", e);
		}
    }
}
