package it.unifi.dinfo.rulesengine.rulesengines.realtime.prato;

import it.unifi.dinfo.rulesengine.rules.ExploitNaturalLight;
import it.unifi.dinfo.rulesengine.rules.PowerPeak;
import it.unifi.dinfo.rulesengine.rules.RealTimeComfortIndex;
import it.unifi.dinfo.rulesengine.rules.RealTimePowerFactor;
import it.unifi.dinfo.rulesengine.rulesengines.RulesSet;
import org.springframework.stereotype.Component;

@Component
public class ExampleRealtimeRules extends RulesSet {


    public ExampleRealtimeRules(){
        RealTimePowerFactor realTimePowerFactor = new RealTimePowerFactor();
        realTimePowerFactor.setURI("gaia-prato/gw1/QG/pwf");
        realTimePowerFactor.setThreshold(0.95);
        rulesEngine.registerRule(realTimePowerFactor);

        ExploitNaturalLight exploitNaturalLight = new ExploitNaturalLight();
        exploitNaturalLight.setExceedingsThreshold(2L);
        rulesEngine.registerRule(exploitNaturalLight);

        RealTimeComfortIndex realTimeComfortIndex = new RealTimeComfortIndex();
        realTimeComfortIndex.setHumidUri("gaia-prato/gw1/weather/humid");
        realTimeComfortIndex.setTempUri("gaia-prato/gw1/weather/temp");
        rulesEngine.registerRule(realTimeComfortIndex);

        PowerPeak powerPeak = new PowerPeak("gaia-prato/gw1/QG/Lighting/actpw");
        rulesEngine.registerRule(powerPeak);

    }

}
