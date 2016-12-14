package it.unifi.dinfo.rules;

import it.unifi.dinfo.service.ScheduledClass;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule
public class HourlyPowerFactor {
    private int threshold = 3;
    String URI;
    long exceedings;

    public HourlyPowerFactor(String URI) {
        this.URI = URI;
    }

    @Condition
    public boolean exceedingsOverThreshold(){
        exceedings = ScheduledClass.getLatestHourFor(URI).stream().filter(m -> m.getReading() < threshold).count();
        return exceedings > threshold;
    }

    @Action
    public void log(){
        System.out.println(String.format("More then %d exceedings during last hour [%d].",threshold,exceedings));
    }
}
