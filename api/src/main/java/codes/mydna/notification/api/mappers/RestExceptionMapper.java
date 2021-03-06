package codes.mydna.notification.api.mappers;

import codes.mydna.rest.exceptions.ExceptionResponse;
import codes.mydna.rest.exceptions.RestException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RestExceptionMapper implements ExceptionMapper<RestException> {

    @Override
    public Response toResponse(RestException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setStatusCode(e.getStatusCode());
        response.setMessage(e.getMessage());
        return Response.status(e.getStatusCode()).entity(response).build();
    }
}
