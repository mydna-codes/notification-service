package codes.mydna.notification.consumers;

import codes.mydna.notification.lib.Email;
import codes.mydna.notification.services.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.streaming.common.annotations.StreamListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.Logger;

@ApplicationScoped
public class KafkaConsumer {

    private static final Logger LOG = Logger.getLogger(KafkaConsumer.class.getName());

    @Inject
    private EmailService emailService;

    @StreamListener(topics = {"send_email"})
    public void onMessage(ConsumerRecord<String, String> record) {

        Email email;
        try {
            ObjectMapper mapper = new ObjectMapper();
            email = mapper.readValue(record.value(), Email.class);
        } catch (JsonProcessingException e) {
            LOG.severe("Failed to deserialize email object!");
            return;
        }

        emailService.sendEmail(email);
    }

}
