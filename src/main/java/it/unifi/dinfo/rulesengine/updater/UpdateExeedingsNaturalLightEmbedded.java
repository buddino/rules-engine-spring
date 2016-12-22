package it.unifi.dinfo.rulesengine.updater;

import it.unifi.dinfo.rulesengine.configuration.ContextProvider;
import it.unifi.dinfo.rulesengine.service.GaiaRules;
import it.unifi.dinfo.rulesengine.service.SwaggerClient;
import org.apache.log4j.Logger;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;
import org.springframework.stereotype.Component;


@Component
public class UpdateExeedingsNaturalLightEmbedded implements Updater {
    Logger LOGGER = Logger.getLogger(this.getClass());

    SwaggerClient sparks = ContextProvider.getBean(SwaggerClient.class);
    DB embeddedDB = ContextProvider.getBean(DB.class);

    private double luminosityThreshold = 2.5;
    private double powerThreshold = 1000000.0; // 1 kW
    private double temperatureThreshold = Double.POSITIVE_INFINITY;

    public void update() {
        //Load exceedings map
        HTreeMap<String, Long> map = embeddedDB.hashMap("exeedings", Serializer.STRING, Serializer.LONG).createOrOpen();

        //Retrieve values from the measurements map
        double light = GaiaRules.getLatestFor("gaia-prato/gw1/weather/light").getReading();
        double actpw = GaiaRules.getLatestFor("gaia-prato/gw1/QG/Lighting/actpw").getReading();
        double externalTemperature = GaiaRules.getLatestFor("gaia-prato/gw1/weather/temp").getReading();

        //Verify condition and update exceedings value
        Long exceedings = map.getOrDefault("gaia-prato/gw1/weather/light", 0L);
        boolean condition = light > luminosityThreshold && actpw > powerThreshold && externalTemperature < temperatureThreshold;
        if (condition) {
            map.put("gaia-prato/gw1/weather/light", ++exceedings);
            LOGGER.info("Exceedings: "+exceedings);
            //System.out.println(String.format("Temp: %.1f\nPower: %.1f\nLight: %.1f\n", externalTemperature, actpw, light));
        }
        else {
            map.put("gaia-prato/gw1/weather/light", 0L);
        }
        embeddedDB.commit();
    }


}
