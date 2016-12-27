package it.unifi.dinfo.rulesengine.counter;

import it.unifi.dinfo.rulesengine.configuration.ContextProvider;
import it.unifi.dinfo.rulesengine.service.MeasurementRepository;
import it.unifi.dinfo.rulesengine.service.SwaggerClient;
import org.apache.log4j.Logger;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

public class UpdatePowerPeak implements Counter {
    Logger LOGGER = Logger.getLogger(this.getClass());

    SwaggerClient sparks = ContextProvider.getBean(SwaggerClient.class);
    DB embeddedDB = ContextProvider.getBean(DB.class);
    MeasurementRepository measurements = ContextProvider.getBean(MeasurementRepository.class);

    public UpdatePowerPeak(String URI) {
        this.URI = URI;
    }

    public UpdatePowerPeak() {
    }

    private String URI = null;
    private double peakThreshold = getPeakThresholdFromAnalytics();

    @Override
    public void update() {
        //Load exceedings map
        HTreeMap<String, Long> map = embeddedDB.hashMap("exeedings", Serializer.STRING, Serializer.LONG).createOrOpen();
        Long exceedings = map.getOrDefault(URI, 0L);
        double actpw = measurements.getLatestFor(URI).getReading();
        if (actpw > peakThreshold) {
            map.put(URI, ++exceedings);
            LOGGER.info("Exceedings: " + exceedings);
        } else {
            map.put(URI, 0L);
        }
        embeddedDB.commit();
    }

    private double getPeakThresholdFromAnalytics() {
        //Return 10Kw
        return 6 * 1000 * 1000;
    }

    public double getPeakThreshold() {
        return peakThreshold;
    }

    public void setPeakThreshold(double peakThreshold) {
        this.peakThreshold = peakThreshold;
    }


    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }
}
