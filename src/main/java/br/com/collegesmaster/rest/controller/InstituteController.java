package br.com.collegesmaster.rest.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.collegesmaster.model.impl.InstituteImpl;

@Path("/institutes")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface InstituteController extends GenericController<InstituteImpl> {
	
	@GET
	@Path("/names")
	List<InstituteImpl> findNames();
	
	@GET
	@Path("/fetching_couses")
	List<InstituteImpl> findFetchingCourses();
}
