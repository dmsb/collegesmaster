package br.com.collegesmaster.business;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import br.com.collegesmaster.model.Model;

public interface BasicCrudOperation <M extends Model> {
	
	@POST
	@Path("/create")
	public Boolean create(M imodel);
	
	@PUT
	@Path("/update/{id}")
	public M update(M imodel) ;
	
	@DELETE
	@Path("/delete/{id}")
	public Boolean remove(M imodel);
	
	@GET
	@Path("/find/{id}")
	public M findById(@PathParam("id") Integer id);
	
	@GET
	List<M> findAll(@Context UriInfo requestInfo);
	
	public List<M> findAll();
	
}