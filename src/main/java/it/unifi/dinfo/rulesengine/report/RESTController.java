package it.unifi.dinfo.rulesengine.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.client.model.ResourceDataDTO;
import it.unifi.dinfo.rulesengine.notification.GAIANotification;
import it.unifi.dinfo.rulesengine.service.MeasurementRepository;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@RestController
public class RESTController {

    @Autowired
    DB embeddedDB;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    EventLogger eventLogger;

    @Autowired
    MeasurementRepository measurements;

    @RequestMapping("/exceedings")
    public HTreeMap<String, Long> test(){
       return embeddedDB.hashMap("exeedings", Serializer.STRING, Serializer.LONG).open();
    }

    @RequestMapping("/latest")
    public Map<String, ResourceDataDTO> latest(){
         return measurements.getLatestReadings();
    }

    @RequestMapping("/lastupdate")
    public Date getLastUpdate(){
        return measurements.getLastUpdate();
    }

    @RequestMapping("/report")
    public Set<GAIANotification> getReport(){
        Set<GAIANotification> report = eventLogger.getEvents();
        return report;
    }

}
