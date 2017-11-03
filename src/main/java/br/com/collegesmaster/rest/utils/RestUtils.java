package br.com.collegesmaster.rest.utils;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedMap;

public class RestUtils {
	
	private RestUtils() {
		
	}

	public static Map<String, Object> buildPredicatesFromRequest(
			final MultivaluedMap<String, String> queryParameters) {

		if (queryParameters != null) {
			final Map<String, Object> predicates = new HashMap<>();
			queryParameters.forEach((key, value) -> predicates.put(key, value.get(0)));
			return predicates;
		} else {
			throw new BadRequestException();
		}
	}
}
