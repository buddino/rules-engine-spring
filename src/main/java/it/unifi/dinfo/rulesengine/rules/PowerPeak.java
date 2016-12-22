package it.unifi.dinfo.rulesengine.rules;

import it.unifi.dinfo.rulesengine.configuration.ContextProvider;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

@Rule
public class PowerPeak {

    DB embeddedDB = ContextProvider.getBean(DB.class);

    long exceedingsThreshold = 4;
    HTreeMap<String, Long> map = embeddedDB.hashMap("exeedings", Serializer.STRING, Serializer.LONG).createOrOpen();


    @Condition
    public boolean condition(){
        Long exceedings = map.getOrDefault("gaia-prato/gw1/QG/Lighting/actpw", 0L);
        return exceedings > exceedingsThreshold;
    }

    @Action
    public void action(){

    }

}
