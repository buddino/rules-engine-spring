package it.unifi.dinfo.rulesengines;

import it.unifi.dinfo.rules.RealTimePowerFactor;
import org.springframework.stereotype.Component;

@Component
public class QGRealTimeRules extends RulesSet{

    public QGRealTimeRules(){
        RealTimePowerFactor pwf = new RealTimePowerFactor("gaia-prato/gw1/QG/pwf");
        pwf.setThreshold(900.0);
        rulesEngine.registerRule(pwf);
    }

}
