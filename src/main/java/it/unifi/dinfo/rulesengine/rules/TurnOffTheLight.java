package it.unifi.dinfo.rulesengine.rules;

import it.unifi.dinfo.rulesengine.configuration.ContextProvider;
import it.unifi.dinfo.rulesengine.service.MeasurementRepository;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule
public class TurnOffTheLight {
    MeasurementRepository measurements = ContextProvider.getBean(MeasurementRepository.class);

    @Condition
    public boolean condition(){
        double lastHourAverage = measurements.getLatestHourFor("gaia-prato/gw1/Geom/1F/Rooms")
                .stream().mapToDouble(m->m.getReading()).average().getAsDouble();
        return lastHourAverage > 0.0;
    }

    @Action
    public void action(){
            //TODO
    }
}
