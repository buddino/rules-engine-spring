package it.unifi.dinfo.rulesengine.rules;

import it.unifi.dinfo.rulesengine.configuration.ContextProvider;
import it.unifi.dinfo.rulesengine.service.MeasurementRepository;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule
public class HourlyPowerFactor {
    MeasurementRepository measurements = ContextProvider.getBean(MeasurementRepository.class);
    private int threshold = 3;
    String URI;
    long exceedings;

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    @Condition
    public boolean exceedingsOverThreshold(){
        exceedings = measurements.getLatestHourFor(URI).stream().filter(m -> m.getReading() < threshold).count();
        return exceedings > threshold;
    }

    @Action
    public void log(){
        System.out.println(String.format("More then %d exceedings during last hour [%d].",threshold,exceedings));
    }
}
