package it.unifi.dinfo.rulesengines;

import it.unifi.dinfo.rules.RealTimeComfortIndex;
import org.springframework.stereotype.Component;

@Component
public class WeatherStationRealTimeRules extends RulesSet {
    public WeatherStationRealTimeRules(){
        RealTimeComfortIndex index = new RealTimeComfortIndex();
        rulesEngine.registerRule(index);
    }
}
