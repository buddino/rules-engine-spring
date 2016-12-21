package it.unifi.dinfo.rulesengine.rulesengines.realtime.prato;

import it.unifi.dinfo.rulesengine.rules.RealTimePowerFactor;
import it.unifi.dinfo.rulesengine.rulesengines.RulesSet;
import org.springframework.stereotype.Component;

@Component
public class QSRealTimeRules extends RulesSet {
    public QSRealTimeRules(){
        RealTimePowerFactor pwf = new RealTimePowerFactor("gaia-prato/gw1/QS/pwf");
        pwf.setThreshold(950.0);
        rulesEngine.registerRule(pwf);
    }
}
