package codes.mydna.notification.services.impl;

import codes.mydna.notification.entities.EmailEntity;
import codes.mydna.notification.lib.Email;
import codes.mydna.notification.mappers.EmailMapper;
import codes.mydna.notification.services.ArchiveService;
import codes.mydna.rest.exceptions.NotFoundException;
import codes.mydna.rest.utils.EntityList;
import codes.mydna.rest.validation.Assert;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ArchiveServiceImpl implements ArchiveService {

    @Inject
    private EntityManager em;

    @Override
    public EntityList<Email> getSavedEmails(QueryParameters qp) {
        List<Email> emails = JPAUtils.queryEntities(em, EmailEntity.class, qp)
                .stream()
                .map(EmailMapper::fromEntity)
                .collect(Collectors.toList());
        Long count = JPAUtils.queryEntitiesCount(em, EmailEntity.class);
        return new EntityList<>(emails, count);
    }

    @Override
    public Email getSavedEmail(String id) {
        Assert.fieldNotEmpty(id, "id");

        Email email = EmailMapper.fromEntity(getEmailEntity(id));
        if (email == null)
            throw new NotFoundException(Email.class, id);

        return email;
    }

    @Override
    public Email saveEmail(Email email) {
        Assert.objectNotNull(email, Email.class);
        Assert.fieldNotEmpty(email.getFrom(), "from", Email.class);
        Assert.fieldNotEmpty(email.getTo(), "to", Email.class);
        Assert.fieldNotEmpty(email.getSubject(), "subject", Email.class);
        Assert.fieldNotEmpty(email.getContent(), "content", Email.class);

        EmailEntity emailEntity = EmailMapper.toEntity(email);

        em.getTransaction().begin();
        em.persist(emailEntity);
        em.getTransaction().commit();

        return EmailMapper.fromEntity(emailEntity);
    }

    @Override
    public boolean deleteEmail(String id) {
        EmailEntity entity = getEmailEntity(id);
        if (entity == null)
            return false;

        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();

        return true;
    }

    private EmailEntity getEmailEntity(String id){
        if (id == null)
            return null;
        return em.find(EmailEntity.class, id);
    }

}
