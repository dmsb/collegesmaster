package br.com.collegesmaster.rest.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/institutes")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface InstituteController {

	@GET
	@Path("/names")
	Response findNames();
	
	@GET
	@Path("/fetching-courses")
	Response findFetchingCourses();
}
