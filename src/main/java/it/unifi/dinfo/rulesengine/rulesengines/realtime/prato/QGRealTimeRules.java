package it.unifi.dinfo.rulesengine.rulesengines.realtime.prato;

import it.unifi.dinfo.rulesengine.rules.ExploitNaturalLight;
import it.unifi.dinfo.rulesengine.rules.RealTimePowerFactor;
import it.unifi.dinfo.rulesengine.rulesengines.RulesSet;
import org.springframework.stereotype.Component;

@Component
public class QGRealTimeRules extends RulesSet {


    public QGRealTimeRules(){
        RealTimePowerFactor pwf = new RealTimePowerFactor();
        pwf.setURI("gaia-prato/gw1/QG/pwf");
        pwf.setThreshold(0.9);
        rulesEngine.registerRule(pwf);

        ExploitNaturalLight naturalLight = new ExploitNaturalLight();
        rulesEngine.registerRule(naturalLight);
    }

}
