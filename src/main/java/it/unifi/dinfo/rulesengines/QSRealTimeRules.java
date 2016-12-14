package it.unifi.dinfo.rulesengines;

import it.unifi.dinfo.rules.RealTimePowerFactor;
import org.springframework.stereotype.Component;

@Component
public class QSRealTimeRules extends RulesSet {
    public QSRealTimeRules(){
        RealTimePowerFactor pwf = new RealTimePowerFactor("gaia-prato/gw1/QS/pwf");
        pwf.setThreshold(950.0);
        rulesEngine.registerRule(pwf);
    }
}
