package codes.mydna.services;

import codes.mydna.lib.Email;
import codes.mydna.utils.EntityList;
import com.kumuluz.ee.rest.beans.QueryParameters;

/**
 * @see codes.mydna.services.impl.ArchiveServiceImpl
 */
public interface ArchiveService {
    public EntityList<Email> getSavedEmails(QueryParameters qp);
    public Email getSavedEmail(String id);
    public Email saveEmail(Email email);
    public boolean deleteEmail(String id);
}
