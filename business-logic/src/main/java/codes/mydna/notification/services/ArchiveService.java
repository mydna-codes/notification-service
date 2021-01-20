package codes.mydna.notification.services;

import codes.mydna.notification.lib.Email;
import codes.mydna.notification.services.impl.ArchiveServiceImpl;
import codes.mydna.rest.utils.EntityList;
import com.kumuluz.ee.rest.beans.QueryParameters;

/**
 * @see ArchiveServiceImpl
 */
public interface ArchiveService {
    EntityList<Email> getSavedEmails(QueryParameters qp);
    Email getSavedEmail(String id);
    Email saveEmail(Email email);
    boolean deleteEmail(String id);
}
