package it.unifi.dinfo.rulesengine.service;

import com.google.gson.Gson;
import io.swagger.client.ApiException;
import io.swagger.client.model.ResourceDataDTO;
import it.unifi.dinfo.rulesengine.api.WSController;
import it.unifi.dinfo.rulesengine.rulesengines.hourly.QGHourly;
import it.unifi.dinfo.rulesengine.rulesengines.realtime.prato.QGRealTimeRules;
import it.unifi.dinfo.rulesengine.rulesengines.realtime.prato.WeatherStationRealTimeRules;
import it.unifi.dinfo.rulesengine.updater.UpdateExeedingsNaturalLightEmbedded;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GaiaRules {
    private final Logger LOGGER = Logger.getRootLogger();
    Gson gson = new Gson();
    static Map<String,ResourceDataDTO> latestReadings;
    static Map<String,List<ResourceDataDTO>> lastHourRedings;

    @Autowired
    SwaggerClient sparks;

    @Autowired
    QGRealTimeRules QGRealTime;
    @Autowired
    QGHourly qgH;
    @Autowired
    WeatherStationRealTimeRules weather;
    @Autowired
    UpdateExeedingsNaturalLightEmbedded updater;
    @Autowired
    WSController ws;



    public static ResourceDataDTO getLatestFor(String URI){
        if(latestReadings.containsKey(URI))
        return latestReadings.get(URI);
        else {
            System.err.println(URI+" Not found");
            return null;
        }
    }
    public static List<ResourceDataDTO> getLatestHourFor(String URI){
        return lastHourRedings.get(URI);
    }

    @Scheduled(fixedDelay = 5000)
    public void getMeasurements()  {
        ws.pushNotification("Hello!");
    }

    //@Scheduled(fixedDelay = 5000)
    public void scheduledMethod() throws ApiException {
        latestReadings = sparks.queryLatest();
        updater.update();
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
