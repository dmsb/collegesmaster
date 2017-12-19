package br.com.collegesmaster.rest.controllers.impl;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import br.com.collegesmaster.model.institute.business.InstituteBusiness;
import br.com.collegesmaster.model.institute.impl.InstituteImpl;
import br.com.collegesmaster.rest.controllers.InstituteController;
import br.com.collegesmaster.rest.controllers.util.CustomResponseBuilder;

@RequestScoped
public class InstituteControllerImpl implements InstituteController {
	
	@Inject
	private InstituteBusiness instituteBusiness;

	@Override
	public Response findNames() {
		final List<InstituteImpl> institutes = instituteBusiness.findNames();
		return CustomResponseBuilder.entityCollectionResponse(institutes);
	}

	@Override
	public Response findFetchingCourses() {
		final List<InstituteImpl> institutes = instituteBusiness.findFetchingCourses();
		return CustomResponseBuilder.entityCollectionResponse(institutes);
	}

}
