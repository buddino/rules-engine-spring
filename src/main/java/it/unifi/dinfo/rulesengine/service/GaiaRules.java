package it.unifi.dinfo.rulesengine.service;

import com.google.gson.Gson;
import io.swagger.client.ApiException;
import it.unifi.dinfo.rulesengine.notification.GAIANotification;
import it.unifi.dinfo.rulesengine.notification.NotificationType;
import it.unifi.dinfo.rulesengine.notification.websocket.WSController;
import it.unifi.dinfo.rulesengine.rulesengines.hourly.QGHourly;
import it.unifi.dinfo.rulesengine.rulesengines.realtime.prato.ExampleRealtimeRules;
import it.unifi.dinfo.rulesengine.rulesengines.realtime.prato.WeatherStationRealTimeRules;
import it.unifi.dinfo.rulesengine.counter.CounterSet;
import it.unifi.dinfo.rulesengine.counter.UpdateExeedingsNaturalLight;
import it.unifi.dinfo.rulesengine.counter.UpdatePowerPeak;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class GaiaRules {
    private final Logger LOGGER = Logger.getRootLogger();
    Gson gson = new Gson();

    @Autowired
    MeasurementRepository measurements;

    @Autowired
    ExampleRealtimeRules QGRealTime;
    @Autowired
    QGHourly qgH;
    @Autowired
    WeatherStationRealTimeRules weather;
    @Autowired
    WSController ws;

    CounterSet QGUpdater = new CounterSet();

    public GaiaRules() {
        //Create Updaters
        QGUpdater = new CounterSet();
        QGUpdater.add(new UpdateExeedingsNaturalLight());
        QGUpdater.add(new UpdatePowerPeak("gaia-prato/gw1/QG/Lighting/actpw"));
    }

    //@Scheduled(fixedDelay = 10000)
    public void testNotification() {
        GAIANotification notification = new GAIANotification();
        notification.setTimestamp(new Date().getTime());
        notification.setRule("Dummy");
        notification.setDescription("Test notification");
        Map<String, Double> map = new HashMap<>();
        map.put("Power factor", Math.random());
        map.put("Sparks", 0.0);
        map.put("CNIT", 1.0);
        notification.setValues(map);
        notification.setType(NotificationType.warning);
        ws.pushNotification(notification);
    }

    @Scheduled(fixedDelay = 30000)
    public void scheduledMethod() throws ApiException {
        measurements.updateLatest();
        QGUpdater.update();
        QGRealTime.fireRules();
        //qs.fireRules();
        //weather.fireRules();
    }

    //@Scheduled(fixedDelay = 900000)
    public void everyHour() throws ApiException {
        //lastHourRedings = sparks.queryLatestHour();
        //qgH.fireRules();
    }


}
