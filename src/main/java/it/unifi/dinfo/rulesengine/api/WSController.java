package it.unifi.dinfo.rulesengine.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WSController {

    private SimpMessagingTemplate template;

    @Autowired
    public WSController(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void pushNotification(String message) {
        this.template.convertAndSend("/topic/greetings", message);
    }
}
