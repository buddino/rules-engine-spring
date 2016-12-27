package it.unifi.dinfo.rulesengine.service;

import io.swagger.client.ApiException;
import io.swagger.client.model.ResourceDataDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class MeasurementRepository {

    Logger LOGGER = Logger.getLogger(this.getClass());
    @Autowired
    SwaggerClient sparks;

    Date lastupdate = null;
    Map<String, ResourceDataDTO> latestReadings = null;
    Map<String, List<ResourceDataDTO>> lastHourRedings = null;

    public Date getLastUpdate() {
        return lastupdate;
    }

    public void updateLatest() {
        try {
            latestReadings = sparks.queryLatest();
            lastupdate = new Date();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public void updateLatestHour() {
        try {
            lastHourRedings = sparks.queryLatestHour();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public Map<String, ResourceDataDTO> getLatestReadings() {
        return Collections.unmodifiableMap(latestReadings);
    }

    public Map<String, List<ResourceDataDTO>> getLastHours() {
        return Collections.unmodifiableMap(lastHourRedings);
    }

    public ResourceDataDTO getLatestFor(String uri) {
        if (latestReadings.containsKey(uri))
            return latestReadings.get(uri);
        LOGGER.error(uri + " not found in map");
        return null;
    }

    public List<ResourceDataDTO> getLatestHourFor(String uri) {
        if (lastHourRedings.containsKey(uri))
            return lastHourRedings.get(uri);
        LOGGER.error(uri + " not found in map");
        return null;
    }
}
