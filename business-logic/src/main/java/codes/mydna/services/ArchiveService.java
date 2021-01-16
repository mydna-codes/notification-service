package codes.mydna.services;

import codes.mydna.lib.Email;
import codes.mydna.utils.EntityList;
import com.kumuluz.ee.rest.beans.QueryParameters;

/**
 * @see codes.mydna.services.impl.ArchiveServiceImpl
 */
public interface ArchiveService {
    EntityList<Email> getSavedEmails(QueryParameters qp);
    Email getSavedEmail(String id);
    Email saveEmail(Email email);
    boolean deleteEmail(String id);
}
