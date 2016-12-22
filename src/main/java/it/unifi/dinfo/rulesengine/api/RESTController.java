package it.unifi.dinfo.rulesengine.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RESTController {

    @Autowired
    DB embeddedDB;

    @Autowired
    ObjectMapper mapper;

    @RequestMapping("/exceedings")
    public String test(){
        HTreeMap<String, Long> exceedingsMap = embeddedDB.hashMap("exeedings", Serializer.STRING, Serializer.LONG).open();
        try {
            return mapper.writeValueAsString(exceedingsMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
