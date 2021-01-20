package codes.mydna.notification.api.resources;

import codes.mydna.auth.common.RealmRole;
import codes.mydna.notification.api.resources.definitions.ArchiveResourceDefinition;
import codes.mydna.notification.lib.Email;
import codes.mydna.notification.services.ArchiveService;
import codes.mydna.rest.http.Headers;
import codes.mydna.rest.utils.EntityList;
import codes.mydna.rest.utils.QueryParametersBuilder;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.auth.keycloak.annotations.RolesAllowed;
import com.mjamsek.auth.keycloak.annotations.SecureResource;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("archive")
@Tag(name = "Archive")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecureResource
@RequestScoped
public class ArchiveResource implements ArchiveResourceDefinition {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ArchiveService archiveService;

    @Override
    @RolesAllowed(RealmRole.ADMIN)
    public Response getSavedEmails() {
        QueryParameters qp = QueryParametersBuilder.buildDefault(uriInfo.getRequestUri().getRawQuery());
        EntityList<Email> emails = archiveService.getSavedEmails(qp);
        return Response.ok().entity(emails.getList()).header(Headers.XTotalCount, emails.getCount()).build();
    }

    @Override
    @RolesAllowed(RealmRole.ADMIN)
    public Response getSavedEmail(String id) {
        Email email = archiveService.getSavedEmail(id);
        return Response.ok().entity(email).build();
    }
}
