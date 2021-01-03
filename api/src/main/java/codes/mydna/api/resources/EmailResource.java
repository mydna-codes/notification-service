package codes.mydna.api.resources;

import codes.mydna.api.resources.definitions.EmailResourceDefinition;
import codes.mydna.lib.Email;
import codes.mydna.services.EmailService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("email")
@Tag(name = "Notifications")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class EmailResource implements EmailResourceDefinition {

    @Inject
    private EmailService emailService;

    @Override
    public Response sendEmail(Email email) {
        Email sentEmail = emailService.sendEmail(email);
        return Response.ok(sentEmail).build();
    }
}
