package it.unifi.dinfo.rulesengine.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.DataapicontrollerApi;
import io.swagger.client.api.ResourceapicontrollerApi;
import io.swagger.client.helper.Configurator;
import io.swagger.client.model.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SwaggerClient {
    public final DataapicontrollerApi dataApi = new DataapicontrollerApi();
    public final ResourceapicontrollerApi resApi = new ResourceapicontrollerApi();
    Gson gson = new Gson();

    @Autowired
    MeterMap meterMap;

    public SwaggerClient() {
        ApiClient client = Configurator.getApiClient();
        client.setConnectTimeout(30000);
        dataApi.setApiClient(client);
        resApi.setApiClient(client);
        System.out.println(client.getConnectTimeout());
    }

    public void setProperty(String resourceURI, Object value, String key){
        long id = meterMap.getId(resourceURI);
        ResourcePropertyDTO resourcePropertyDTO = new ResourcePropertyDTO();
        resourcePropertyDTO.setName(value.toString());
        resourcePropertyDTO.setPredicate(key);
        resourcePropertyDTO.setResourceId(id);
        ResourcePropertyListDTO resourcePropertyListDTO = new ResourcePropertyListDTO();
        resourcePropertyListDTO.addPropertiesItem(resourcePropertyDTO);
        try {
            //Add property
            resApi.addResourcePropertyUsingPOST(id, resourcePropertyListDTO);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public String readProperty(String resourceURI, String key){
        long id = meterMap.getId(resourceURI);
        try {
            ResourcePropertyListDTO resourcePropertyListDTO = resApi.listResourcePropertiesUsingGET(id);
            Optional<ResourcePropertyDTO> property = resourcePropertyListDTO.getProperties().stream().filter(p -> p.getPredicate() == key).findFirst();
            if(property.isPresent()){
                String value = property.get().getName();
                return value;
            }
            else{
                System.err.println("Property not found");
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String readPropertyOrSetDefault(String resourceURI, String key, Object defaultValue){
        long id = meterMap.getId(resourceURI);
        try {
            ResourcePropertyListDTO resourcePropertyListDTO = resApi.listResourcePropertiesUsingGET(id);
            Optional<ResourcePropertyDTO> property = resourcePropertyListDTO.getProperties().stream().filter(p -> p.getPredicate().equals(key)).findFirst();
            if(property.isPresent()){
                String value = property.get().getName();
                return value;
            }
            else{
                System.err.println("Propoerty not found");
                setProperty(resourceURI, defaultValue, key);
                return defaultValue.toString();
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, ResourceDataDTO> queryLatest() throws ApiException {
        Map<String, ResourceDataDTO> readings = new HashMap<>();
        QueryLatestResourceDataDTO query = new QueryLatestResourceDataDTO();
        for (Long resourceId : meterMap.getIDs()) {
            QueryResourceDataCriteriaDTO crtierium = new QueryResourceDataCriteriaDTO();
            crtierium.setResourceID(resourceId);
            query.addQueriesItem(crtierium);
        }
        QueryLatestResourceDataResultDTO result = dataApi.queryLatestResourcesDataUsingPOST(query);
        Set<String> keySet = result.getResults().keySet();
        for (String requestString : keySet) {
            JsonObject obj = gson.fromJson(requestString, JsonObject.class);
            String resourceURI = obj.get("resourceURI").toString().replace("\"", "");
            ResourceDataDTO res = result.getResults().get(requestString);
            readings.put(resourceURI, res);
        }
        return readings;
    }


    public Map<String, List<ResourceDataDTO>> queryLatestHour() throws ApiException {
        Map<String, List<ResourceDataDTO>> readings = new HashMap<>();

        DateTime now = new DateTime();
        QueryTimeRangeResourceDataDTO query = new QueryTimeRangeResourceDataDTO();
        for (Long resourceId : meterMap.getIDs()) {
            QueryTimeRangeResourceDataCriteriaDTO crtierium = new QueryTimeRangeResourceDataCriteriaDTO();
            crtierium.setResourceID(resourceId);
            crtierium.setFrom(now.minusHours(1).getMillis());
            crtierium.setTo(now.getMillis());
            crtierium.setGranularity(QueryTimeRangeResourceDataCriteriaDTO.GranularityEnum._5MIN);
            query.addQueriesItem(crtierium);
        }

        QueryTimeRangeResourceDataResultDTO result = dataApi.queryTimeRangeResourcesDataUsingPOST(query);
        Set<String> keySet = result.getResults().keySet();
        for (String requestString : keySet) {
            JsonObject obj = gson.fromJson(requestString, JsonObject.class);
            String resourceURI = obj.get("resourceURI").toString().replace("\"", "");
            List measurements = result.getResults().get(requestString);
            JsonArray arr = (JsonArray) gson.toJsonTree(measurements);
            List<ResourceDataDTO> list = gson.fromJson(arr, new TypeToken<List<ResourceDataDTO>>() {
            }.getType());
            readings.put(resourceURI, list);
        }
        return readings;
    }

}
