package br.com.collegesmaster.rest.util;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

public abstract class RestUtils {

	public static Map<String, Object> buildPredicatesFromRequest(
			final UriInfo requestInfo) {

		final MultivaluedMap<String, String> requestPredicates;

		if (requestInfo != null) {
			requestPredicates = requestInfo.getQueryParameters();
			final Map<String, Object> predicates = new HashMap<>();
			requestPredicates.forEach((key, value) -> predicates.put(key, value.get(0)));
			return predicates;
		} else {
			throw new BadRequestException();
		}
	}
}
