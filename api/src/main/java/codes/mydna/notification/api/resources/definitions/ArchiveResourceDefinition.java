package codes.mydna.notification.api.resources.definitions;

import codes.mydna.notification.lib.Email;
import codes.mydna.rest.exceptions.RestException;
import codes.mydna.rest.openapi.examples.OpenApiExceptionExamples;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public interface ArchiveResourceDefinition {

    @Operation(
            description = "Returns list of saved emails.",
            summary = "Get list of saved emails"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Email list returned successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.ARRAY,
                                    implementation = Email.class
                                    //example = OpenApiArchiveExamples.Responses.GET_SAVED_EMAILS
                            )
                    ),
                    headers = {@Header(name = "X-Total-Count", schema = @Schema(type = SchemaType.INTEGER))}
            )
    })
    @GET
    public Response getSavedEmails();

    @Operation(
            description = "Returns a saved email found by id.",
            summary = "Get saved email by id"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Email object returned successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.OBJECT,
                                    implementation = Email.class
                                    //example = OpenApiArchiveExample.EXAMPLE_ID
                            )
                    )
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Empty or invalid input.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.OBJECT,
                                    implementation = RestException.class,
                                    example = OpenApiExceptionExamples.Response.BAD_REQUEST
                            )
                    )
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Object with provided id does not exist.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.OBJECT,
                                    implementation = RestException.class,
                                    example = OpenApiExceptionExamples.Response.NOT_FOUND
                            )
                    )
            )
    })
    @GET
    @Path("{id}")
    public Response getSavedEmail(
            @Parameter(
                    required = true,
                    description = "Email's id"
                    //example = OpenApiArchiveExample.EXAMPLE_ID
            )
            @PathParam("id") String id);

}
