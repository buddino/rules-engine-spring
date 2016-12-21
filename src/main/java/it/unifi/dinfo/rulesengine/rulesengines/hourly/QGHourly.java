package it.unifi.dinfo.rulesengine.rulesengines.hourly;

import it.unifi.dinfo.rulesengine.rules.HourlyPowerFactor;
import it.unifi.dinfo.rulesengine.rulesengines.RulesSet;
import org.springframework.stereotype.Component;

@Component
public class QGHourly extends RulesSet {
    public QGHourly() {
        rulesEngine.registerRule(new HourlyPowerFactor("gaia-prato/gw1/QG/pwf"));
    }
}
