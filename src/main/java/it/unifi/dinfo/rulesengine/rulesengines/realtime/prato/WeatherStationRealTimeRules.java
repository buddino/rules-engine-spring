package it.unifi.dinfo.rulesengine.rulesengines.realtime.prato;

import it.unifi.dinfo.rulesengine.rules.RealTimeComfortIndex;
import it.unifi.dinfo.rulesengine.rulesengines.RulesSet;
import org.springframework.stereotype.Component;

@Component
public class WeatherStationRealTimeRules extends RulesSet {
    public WeatherStationRealTimeRules(){
        RealTimeComfortIndex index = new RealTimeComfortIndex();
        rulesEngine.registerRule(index);
    }
}
