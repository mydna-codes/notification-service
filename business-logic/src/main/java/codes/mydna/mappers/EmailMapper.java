package codes.mydna.mappers;

import codes.mydna.entities.EmailEntity;
import codes.mydna.lib.Email;

public class EmailMapper {

    public static Email fromEntity(EmailEntity entity){
        if(entity == null)
            return null;
        Email email = new Email();
        BaseMapper.fromEntity(entity, email);
        email.setTo(entity.getTo());
        email.setFrom(entity.getFrom());
        email.setSubject(entity.getSubject());
        email.setContent(entity.getContent());
        return email;
    }

    public static EmailEntity toEntity(Email email){
        if(email == null)
            return null;
        EmailEntity entity = new EmailEntity();
        entity.setTo(email.getTo());
        entity.setFrom(email.getFrom());
        entity.setSubject(email.getSubject());
        entity.setContent(email.getContent());
        return entity;
    }

}
