package it.unifi.dinfo.rulesengine.rules;

import it.unifi.dinfo.rulesengine.service.GaiaRules;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

@Rule
public class PowerPeak {
    long exceedingsThreshold = 4;
    HTreeMap<String, Long> map = GaiaRules.db.hashMap("exeedings", Serializer.STRING, Serializer.LONG).createOrOpen();


    @Condition
    public boolean condition(){
        Long exceedings = map.getOrDefault("gaia-prato/gw1/QG/Lighting/actpw", 0L);
        return exceedings > exceedingsThreshold;
    }

    @Action
    public void action(){

    }

}
