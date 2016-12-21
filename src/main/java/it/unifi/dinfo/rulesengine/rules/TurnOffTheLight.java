package it.unifi.dinfo.rulesengine.rules;

import it.unifi.dinfo.rulesengine.service.GaiaRules;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule
public class TurnOffTheLight {


    @Condition
    public boolean condition(){
        double lastHourAverage = GaiaRules.getLatestHourFor("gaia-prato/gw1/Geom/1F/Rooms")
                .stream().mapToDouble(m->m.getReading()).average().getAsDouble();
        return lastHourAverage > 0.0;
    }

    @Action
    public void action(){
            //TODO
    }
}
