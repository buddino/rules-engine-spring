package it.unifi.dinfo.rulesengines;

import it.unifi.dinfo.rules.HourlyPowerFactor;
import org.springframework.stereotype.Component;

@Component
public class QGHourly extends RulesSet{
    public QGHourly() {
        rulesEngine.registerRule(new HourlyPowerFactor("gaia-prato/gw1/QG/pwf"));
    }
}
