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
public class RealTimeComfortIndex extends GaiaRule{
    private final Logger LOGGER = Logger.getRootLogger();
    MeasurementRepository measurements = ContextProvider.getBean(MeasurementRepository.class);

    String tempUri, humidUri;



    private Double threshold = 20.0;
    private Double index, temp, humid;

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    @Condition
    public boolean overThreshold() {
        temp = measurements.getLatestFor(tempUri).getReading();
        humid = measurements.getLatestFor(humidUri).getReading();
        index = -8.7847 + 1.6114 * temp + 2.3385 * humid
                - 0.1461 * humid * temp - 0.0123 * temp * temp - 0.0164 * humid * humid
                + 2.2117 * Math.pow(10, -3) * temp * temp * humid
                + 7.2546 * Math.pow(10, -4) * temp * humid * humid
                - 3.5820 * Math.pow(10, -6) * temp * temp * humid * humid;
        return index > threshold;
    }

    @Action
    public void log() {
        GAIANotification notification = new GAIANotification();
        notification.setTimestamp(new Date().getTime());
        notification.setRule(this.getClass().getSimpleName());
        notification.setDescription("Comfort index over the threshold");
        Map<String,Double> map = new HashMap<>();
        map.put("Threshold", threshold);
        map.put("Temperature", temp);
        map.put("Humidity", humid);
        map.put("comfort index", index);
        notification.setValues(map);
        notification.setType(NotificationType.warning);
        websocket.pushNotification(notification);
        eventLogger.addEvent(notification);
    }

    public String getTempUri() {
        return tempUri;
    }

    public void setTempUri(String tempUri) {
        this.tempUri = tempUri;
    }

    public String getHumidUri() {
        return humidUri;
    }

    public void setHumidUri(String humidUri) {
        this.humidUri = humidUri;
    }
}
