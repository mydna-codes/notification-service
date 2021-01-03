package codes.mydna.api.resources.definitions;

import codes.mydna.exceptions.RestException;
import codes.mydna.lib.Email;
import codes.mydna.openapi.examples.OpenApiExceptionExamples;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

public interface EmailResourceDefinition {

    @Operation(
            description = "Send email.",
            summary = "Send email."
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Email sent successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.OBJECT,
                                    implementation = Email.class
                                    //example = OpenApiEmailExamples.Responses.
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
                    responseCode = "500",
                    description = "Internal server error.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.OBJECT,
                                    implementation = RestException.class,
                                    example = OpenApiExceptionExamples.Response.INTERNAL_SERVER_ERROR
                            )
                    )
            )
    })
    @RequestBody(
            required = true,
            description = "Email to be sent",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            type = SchemaType.OBJECT,
                            implementation = Email.class
                            //example = OpenApiEmailExamples.Requests.
                    )
            )
    )
    @POST
    public Response sendEmail(Email email);

}
