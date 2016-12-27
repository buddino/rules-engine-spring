package it.unifi.dinfo.rulesengine.rules;

import it.unifi.dinfo.rulesengine.configuration.ContextProvider;
import it.unifi.dinfo.rulesengine.notification.amqp.SenderService;
import it.unifi.dinfo.rulesengine.notification.websocket.WSController;
import it.unifi.dinfo.rulesengine.report.EventLogger;
import it.unifi.dinfo.rulesengine.service.MeasurementRepository;
import org.mapdb.DB;

public abstract class GaiaRule {
    DB embeddedDB = ContextProvider.getBean(DB.class);
    SenderService amqpSenderService = ContextProvider.getBean(SenderService.class);
    WSController websocket = ContextProvider.getBean(WSController.class);
    EventLogger eventLogger = ContextProvider.getBean(EventLogger.class);
    MeasurementRepository measurementRepository = ContextProvider.getBean(MeasurementRepository.class);

}
