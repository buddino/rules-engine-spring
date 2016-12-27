package it.unifi.dinfo.rulesengine.report;

import it.unifi.dinfo.rulesengine.notification.GAIANotification;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EventLogger {

    @Autowired
    DB embeddedDB;

    HTreeMap.KeySet<GAIANotification> report;

    @PostConstruct
    public void init(){
        report = (HTreeMap.KeySet<GAIANotification>) embeddedDB.hashSet("report").createOrOpen();
        GAIANotification g = new GAIANotification();
        g.setTimestamp(new Date().getTime());
        g.setDescription("Ciao");
        try{
        report.add(g);
        embeddedDB.commit();}
        catch (Exception e){
            System.err.println(e);
        }
    }


    public void addEvent(GAIANotification n){
        report.add(n);
        embeddedDB.commit();
    }

    public List<GAIANotification> getEvents(Date from, Date to){
         return report.stream().filter(notification -> notification.getTimestamp() > from.getTime() && notification.getTimestamp() < to.getTime())
                 .collect(Collectors.toList());
    }

    public Set<GAIANotification> getEvents(){
        return report;
    }
}
