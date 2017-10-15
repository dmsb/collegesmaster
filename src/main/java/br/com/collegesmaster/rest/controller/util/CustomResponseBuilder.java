package br.com.collegesmaster.rest.controller.util;


import java.util.Collection;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections.CollectionUtils;

import br.com.collegesmaster.model.Model;

public class CustomResponseBuilder {

	private CustomResponseBuilder() {
		
	}
	
	public static <T extends Model> Response entityCollectionResponse(final Collection<T> entities) {
		if (CollectionUtils.isNotEmpty(entities)) {
			return Response.ok().entity(entities).build();
		} else {
			return Response.status(Status.NO_CONTENT).build();
		}
	}
}
