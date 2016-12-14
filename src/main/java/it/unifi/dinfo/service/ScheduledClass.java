package it.unifi.dinfo.service;

import com.google.gson.Gson;
import io.swagger.client.ApiException;
import io.swagger.client.model.ResourceDataDTO;
import it.unifi.dinfo.rulesengines.QGHourly;
import it.unifi.dinfo.rulesengines.QGRealTimeRules;
import it.unifi.dinfo.rulesengines.QSRealTimeRules;
import it.unifi.dinfo.rulesengines.WeatherStationRealTimeRules;
import it.unifi.dinfo.updater.UpdateExeedingsNaturalLight;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ScheduledClass {
    private final Logger LOGGER = Logger.getRootLogger();
    Gson gson = new Gson();
    static Map<String,ResourceDataDTO> latestReadings;
    static Map<String,List<ResourceDataDTO>> lastHourRedings;

    @Autowired
    SwaggerClient sparks;



    @Autowired
    QGRealTimeRules qg;
    @Autowired
    QSRealTimeRules qs;
    @Autowired
    QGHourly qgH;
    @Autowired
    WeatherStationRealTimeRules weather;
    @Autowired
    UpdateExeedingsNaturalLight updater;

    public static ResourceDataDTO getLatestFor(String URI){
        return latestReadings.get(URI);
    }
    public static List<ResourceDataDTO> getLatestHourFor(String URI){
        return lastHourRedings.get(URI);
    }

    //@Scheduled(fixedDelay = 15000)
    public void getMeasurements() throws ApiException {

    }

    @Scheduled(fixedDelay = 30000)
    public void scheduledMethod() throws ApiException {
        latestReadings = sparks.queryLatest();
        updater.update();
        //qg.fireRules();
        //qs.fireRules();
        //weather.fireRules();
    }

    //@Scheduled(fixedDelay = 900000)
    public void everyHour() throws ApiException {
        //lastHourRedings = sparks.queryLatestHour();
        //qgH.fireRules();
    }




}
