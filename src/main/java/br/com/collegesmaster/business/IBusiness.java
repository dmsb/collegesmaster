package br.com.collegesmaster.business;

import static javax.ejb.TransactionAttributeType.NOT_SUPPORTED;
import static javax.ejb.TransactionAttributeType.REQUIRED;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.com.collegesmaster.model.IModel;
import br.com.collegesmaster.model.impl.Model;

public interface IBusiness <I extends IModel, C extends Model> {
	
	@POST
	@Path("/create")
	@TransactionAttribute(REQUIRED)
	public void create(I imodel);
	
	@PUT
	@Path("/update/{id}")
	@TransactionAttribute(REQUIRED)
	public I update(I imodel) ;
	
	@DELETE
	@Path("/delete/{id}")
	@TransactionAttribute(REQUIRED)
	public void remove(I imodel);
	
	@GET
	@Path("/get/{id}")
	@TransactionAttribute(NOT_SUPPORTED)
	public I findById(@PathParam("id") Integer id);
	
	@GET
	@Path("/list")
	@TransactionAttribute(REQUIRED)
	public List<C> findAll();

}