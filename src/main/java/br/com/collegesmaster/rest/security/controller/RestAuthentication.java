package br.com.collegesmaster.rest.security.controller;

import java.io.Serializable;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.com.collegesmaster.security.model.Credentials;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RestAuthentication extends Serializable {

	@POST
    @Path("login")
    public Response login(@Context UriInfo uriInfo,
        @Context HttpHeaders httpHeaders,
        Credentials credentials);
	
}
