package br.com.collegesmaster.rest.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.collegesmaster.exception.BusinessException;

@Provider
public class ExceptionHandler <T extends BusinessException> implements ExceptionMapper<T> {

	@Override
	public Response toResponse(T exception) {
		return Response.status(Response.Status.NOT_FOUND).build();
	}

}
