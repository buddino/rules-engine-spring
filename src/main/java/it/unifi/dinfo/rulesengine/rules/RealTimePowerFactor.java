package it.unifi.dinfo.rulesengine.rules;

import it.unifi.dinfo.rulesengine.configuration.ContextProvider;
import it.unifi.dinfo.rulesengine.notification.GAIANotification;
import it.unifi.dinfo.rulesengine.notification.NotificationType;
import it.unifi.dinfo.rulesengine.service.MeasurementRepository;
import org.apache.log4j.Logger;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Rule
public class RealTimePowerFactor extends GaiaRule {
    Logger LOGGER = Logger.getLogger(this.getClass());
    MeasurementRepository measurements = ContextProvider.getBean(MeasurementRepository.class);

    private String URI;
    private Double threshold = 0.95;
    Double value;

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    @Condition
    public boolean isBelowThreshold() {
        if (URI == null) {
            System.err.println("URI NOT SET");
        }
        value = measurements.getLatestFor(URI).getReading();
        return value < threshold;
    }

    @Action
    public void log() {
        GAIANotification notification = new GAIANotification();
        notification.setTimestamp(new Date().getTime());
        notification.setRule(this.getClass().getSimpleName());
        notification.setDescription(String.format("Power factor [%.2f] below the threshold [%.2f]", value, threshold));
        Map<String,Double> map = new HashMap<>();
        map.put("Power factor",value);
        notification.setValues(map);
        notification.setType(NotificationType.error);
        websocket.pushNotification(notification);
        eventLogger.addEvent(notification);
    }

}
