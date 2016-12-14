package it.unifi.dinfo.rules;

import it.unifi.dinfo.service.ScheduledClass;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule
public class RealTimePowerFactor {
    private String URI;
    private Double threshold = 800.0;

    public RealTimePowerFactor(String URI) {
        this.URI = URI;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    @Condition
    public boolean isBelowThreshold() {
        if(URI==null){
            System.err.println("URI NOT SET");
        }
        Double value = ScheduledClass.getLatestFor(URI).getReading();
        return value < threshold;
    }

    @Action
    public void log() {
        System.err.println(String.format("Power factor [%s] below the threshold [%.1f < %.1f]",URI,ScheduledClass.getLatestFor(URI).getReading(),threshold));
    }

}
