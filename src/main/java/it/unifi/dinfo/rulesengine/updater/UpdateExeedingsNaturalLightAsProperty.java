package it.unifi.dinfo.rulesengine.updater;

import it.unifi.dinfo.rulesengine.service.GaiaRules;
import it.unifi.dinfo.rulesengine.service.SwaggerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateExeedingsNaturalLightAsProperty implements Updater{
    Long exceedings;


    @Autowired
    SwaggerClient sparks;

    private double luminosityThreshold = 2.5;
    private double powerThreshold = 100.0;
    private double temperatureDifferenceThreshold = 10.0;

    public void update() {
        double light = GaiaRules.getLatestFor("gaia-prato/gw1/weather/light").getReading();
        //double actpw = ScheduledClass.getLatestFor("gaia-prato/gw1/weather/QG/Lighting/actpw").getReading();
        double actpw = getActPw();
        double occupancy = computeOccupancy();
        double tempDiff = computeTempDifference();
        //System.out.println(String.format("Active power: %.1f\nOccupancy: %.1f\nDifferential temperature: %.1f\nLight: %.1f ", actpw, occupancy, tempDiff, light));
        String exceedings = sparks.readPropertyOrSetDefault("gaia-prato/gw1/weather/light", "exceedings", 0);
        this.exceedings = Long.parseLong(exceedings);
        boolean condition = light > luminosityThreshold && actpw > powerThreshold && tempDiff < temperatureDifferenceThreshold;
        if(condition) {
            System.err.println("Natural light exploitable!");
            sparks.setProperty("gaia-prato/gw1/weather/light", (++this.exceedings).toString(), "exceedings");
            System.err.println("Exceedings: "+exceedings);
        }
        else{
            System.out.println("Ok!");
            sparks.setProperty("gaia-prato/gw1/weather/light", 0, "exceedings");
        }
    }


    /**
     * Mock occupancy
     * @return a probability of occupancy
     */
    private double computeOccupancy(){
        return Math.random();
    }

    private double computeTempDifference(){
        return  Math.random()*15.0;
    }
    private double getActPw(){
        return Math.random() * 200.0;
    }


}
