package it.unifi.dinfo.rules;

import it.unifi.dinfo.service.ScheduledClass;
import org.apache.log4j.Logger;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule
public class RealTimeComfortIndex {
    private final Logger LOGGER = Logger.getRootLogger();
    private Double threshold = 20.0;
    private Double index, temp, humid;

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    @Condition
    public boolean overThreshold() {
        temp = ScheduledClass.getLatestFor("gaia-prato/gw1/weather/temp").getReading();
        humid = ScheduledClass.getLatestFor("gaia-prato/gw1/weather/humid").getReading();
        index = -8.7847 + 1.6114 * temp + 2.3385 * humid
                - 0.1461 * humid * temp - 0.0123 * temp * temp - 0.0164 * humid * humid
                + 2.2117 * Math.pow(10, -3) * temp * temp * humid
                + 7.2546 * Math.pow(10, -4) * temp * humid * humid
                - 3.5820 * Math.pow(10, -6) * temp * temp * humid * humid;
        return index > threshold;
    }

    @Action
    public void log() {
        LOGGER.info(String.format("Comfort index over the threshold [%.1f > %.1f]", index, threshold));
    }

}
