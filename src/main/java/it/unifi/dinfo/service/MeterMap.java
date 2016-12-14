package it.unifi.dinfo.service;

import io.swagger.client.ApiException;
import io.swagger.client.model.ResourceListDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class MeterMap {

    private final Logger LOGGER = Logger.getRootLogger();
    @Autowired
    SwaggerClient sparks;

    Map<String, Long> metermap = new HashMap<>();
    boolean isConfigured = false;

    @PostConstruct
    public void init() {
        //TODO Si può fare anche il mapping del file
        LOGGER.info("Started resource mapping");
        InputStream in = getClass().getResourceAsStream("/meters.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String meterUri;
        try {
            while ((meterUri = br.readLine()) != null) {
                String uri = "gaia-prato/gw1/" + meterUri;
                ResourceListDTO response = sparks.resApi.listUsingGET1(uri, "", false, "");
                if (response.getResources().size() > 0) {
                    Long resourceId = response.getResources().get(0).getResourceId();
                    metermap.put(uri, resourceId);
                    LOGGER.info("Created mapping " + uri + " : " + resourceId);
                } else {
                    System.err.println("Resource " + uri + " not found");
                }
            }
            isConfigured = true;
            LOGGER.info("Mapping created");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public Long getId(String uri) {
        return metermap.get(uri);
    }

    public boolean isConfigured() {
        return isConfigured;
    }

    public List<Long> getIDs(){
        return new ArrayList(metermap.values());
    }
}
