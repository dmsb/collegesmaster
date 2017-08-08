package br.com.collegesmaster.rest.controller;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import br.com.collegesmaster.model.Model;

public interface GenericController <M extends Model>{
	
	@POST
	@Path("/create")
	public Boolean create(M imodel);
	
	@PUT
	@Path("/update/{id}")
	public M update(M imodel) ;
	
	@DELETE
	@Path("/delete/{id}")
	public Boolean remove(@PathParam("id") Integer id);
	
	@GET
	@Path("/{id}")
	public M findById(@PathParam("id") Integer id);
	
	@GET
	public List<M> findAll(@Context UriInfo info, @Context Request request);

}
