package it.unifi.dinfo.rulesengine.updater;

import it.unifi.dinfo.rulesengine.configuration.ContextProvider;
import it.unifi.dinfo.rulesengine.service.GaiaRules;
import it.unifi.dinfo.rulesengine.service.SwaggerClient;
import org.apache.log4j.Logger;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

public class UpdatePowerPeak implements Updater {
    Logger LOGGER = Logger.getLogger(this.getClass());

    SwaggerClient sparks = ContextProvider.getBean(SwaggerClient.class);
    DB embeddedDB = ContextProvider.getBean(DB.class);

    private double peakThreshold = getPeakThresholdFromAnalytics();

    @Override
    public void update() {
        //Load exceedings map
        HTreeMap<String, Long> map = embeddedDB.hashMap("exeedings", Serializer.STRING, Serializer.LONG).createOrOpen();
        Long exceedings = map.getOrDefault("gaia-prato/gw1/QG/Lighting/actpw", 0L);
        double actpw = GaiaRules.getLatestFor("gaia-prato/gw1/QG/Lighting/actpw").getReading();
        if( actpw > peakThreshold){
            map.put("gaia-prato/gw1/QG/Lighting/actpw",++exceedings);
            LOGGER.info("Exceedings: "+exceedings);
        }
        else {
            map.put("gaia-prato/gw1/QG/Lighting/actpw",0L);
        }
        embeddedDB.commit();
    }

    private double getPeakThresholdFromAnalytics(){
        return 10*1000*1000;
    }
}
