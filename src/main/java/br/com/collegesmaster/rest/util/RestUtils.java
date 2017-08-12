package br.com.collegesmaster.rest.util;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import br.com.collegesmaster.model.Model;
import br.com.collegesmaster.util.FunctionUtils;

public abstract class RestUtils {

	public static <T extends Model> Map<String, Object> buildPredicatesFromRequest(
			final UriInfo requestInfo, final Class<T> modelClass) {

		final MultivaluedMap<String, String> requestPredicates;

		if (requestInfo != null) {
			requestPredicates = requestInfo.getQueryParameters();
			final Map<String, Object> predicates = new HashMap<>();
			requestPredicates.forEach((key, value) -> {
				if (!FunctionUtils.existsFieldInEntity(key, modelClass)) {
					return;
				} else {
					predicates.put(key, value.get(0));
				}
			});
			return predicates;
		} else {
			throw new BadRequestException();
		}
	}
}
