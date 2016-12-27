package it.unifi.dinfo.rulesengine.notification.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unifi.dinfo.rulesengine.notification.GAIANotification;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WSController {
    Logger LOGGER = Logger.getLogger(this.getClass());
    @Autowired
    private ObjectMapper mapper;
    private SimpMessagingTemplate template;

    @Autowired
    public WSController(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void pushNotification(String message) {
        this.template.convertAndSend("/topic/greetings", message);
    }
    public void pushNotification(GAIANotification notification) {
        try {
            LOGGER.info("Sent notification via websocket");
            this.template.convertAndSend("/topic/greetings", mapper.writeValueAsString(notification));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
