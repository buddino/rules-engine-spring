package it.unifi.dinfo.rulesengine.rules;

import it.unifi.dinfo.rulesengine.configuration.ContextProvider;
import it.unifi.dinfo.rulesengine.notification.GAIANotification;
import it.unifi.dinfo.rulesengine.notification.NotificationType;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Rule
public class PowerPeak extends GaiaRule {
    DB embeddedDB = ContextProvider.getBean(DB.class);
    String URI;

    long exceedingsThreshold = 4;
    HTreeMap<String, Long> map = embeddedDB.hashMap("exeedings", Serializer.STRING, Serializer.LONG).createOrOpen();

    public PowerPeak(String URI) {
        this.URI = URI;
    }

    public PowerPeak() {
    }

    @Condition
    public boolean condition() {
        Long exceedings = map.getOrDefault(URI, 0L);
        return exceedings > exceedingsThreshold;
    }

    @Action
    public void action() {
        GAIANotification notification = new GAIANotification();
        notification.setTimestamp(new Date().getTime());
        notification.setRule(this.getClass().getSimpleName());
        notification.setDescription("Peak detected");
        Map<String, Double> map = new HashMap<>();
        notification.setType(NotificationType.error);
        websocket.pushNotification(notification);
    }


    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

}
