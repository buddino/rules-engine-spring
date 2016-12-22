package it.unifi.dinfo.rulesengine.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SenderService {

    private static final String MESSAGE_TEMPLATE = "%s,%f,%d";

    String rabbitQueueSend = "NOTIFICATION-send";

    @Autowired
    RabbitTemplate rabbitTemplate;

    //Removed Async to maintain temporal chronological order
    //@Async
    public void sendNotification(GAIANotification notification) {
        final String message = notification.getRule()+","+notification.getDescription();
        rabbitTemplate.send(rabbitQueueSend, rabbitQueueSend, new Message(message.getBytes(), new MessageProperties()));
    }


}