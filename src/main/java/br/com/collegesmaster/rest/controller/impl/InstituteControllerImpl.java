package br.com.collegesmaster.rest.controller.impl;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import br.com.collegesmaster.business.InstituteBusiness;
import br.com.collegesmaster.model.impl.InstituteImpl;
import br.com.collegesmaster.rest.controller.InstituteController;

@RequestScoped
public class InstituteControllerImpl implements InstituteController {
	
	@Inject
	private InstituteBusiness instituteBusiness;
	
	@Override
	public Boolean create(InstituteImpl imodel) {
		instituteBusiness.create(imodel);
		return TRUE;
	}

	@Override
	public InstituteImpl update(InstituteImpl imodel) {
		return instituteBusiness.update(imodel);
	}

	@Override
	public Boolean remove(Integer id) {
		instituteBusiness.remove(findById(id));
		return TRUE;
	}

	@Override
	public InstituteImpl findById(Integer id) {
		return instituteBusiness.findById(id);
	}

	@Override
	public List<InstituteImpl> findAll(UriInfo info, Request request) {
		final MultivaluedMap<String, String> requestPredicates;

		if(info != null) {
			requestPredicates = info.getQueryParameters();
			final Map<String, Object> predicates = new HashMap<>();
			requestPredicates.forEach((key, value) -> {
				if(!existsField(key)) {
					return;
				} else {
					predicates.put(key, value.get(0));
				}
			});
			return instituteBusiness.findAll(predicates);
		}
		
		return null;
	}

	private Boolean existsField(String field) {
		try {
			InstituteImpl.class.getDeclaredFields();
			InstituteImpl.class.getDeclaredField(field);
			return TRUE;
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			return FALSE;
		}
	}

	@Override
	public List<InstituteImpl> findNames() {
		return instituteBusiness.findNames();
	}

	@Override
	public List<InstituteImpl> findFetchingCourses() {
		return instituteBusiness.findFetchingCourses();
	}

}
