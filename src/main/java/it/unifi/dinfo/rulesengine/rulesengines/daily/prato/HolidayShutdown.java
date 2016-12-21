package it.unifi.dinfo.rulesengine.rulesengines.daily.prato;

import it.unifi.dinfo.rulesengine.helper.Vacation;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Rule
public class HolidayShutdown {

    //TODO One is enough
    Date today = new Date();
    Vacation nextVacation = null;
    long daysToNextVacation = 0;
    @Condition
    public boolean isTodayTheDayBeforeAHoliday() {
        Collection<Vacation> holidays = getHolidays("Test");
        for( Vacation v : holidays){
            long daysToNextVacation = v.getDaysBetween(today);
            if(daysToNextVacation < 10){
                nextVacation = v;
                return true;
            }
        }
        return false;
    }

    @Action
    public void action(){
        System.out.println("Holiday shutdown.\nDuration: "+nextVacation.getDurationInDays());
        //TODO
    }

    //TODO The list should be created only once and updated
    private Collection<Vacation> getHolidays(String schoolID) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<Vacation> vacations = new ArrayList<>();
        try {
            Vacation xmas = new Vacation(sdf.parse("25/12/2016"), sdf.parse("08/01/2017"), "Christmas");
            Vacation fake = new Vacation(sdf.parse("31/12/2016"), sdf.parse("01/12/2016"), "FakeHoliday");
            vacations.add(xmas);
            vacations.add(fake);
        }
        catch (ParseException e){
            System.err.println(e.getMessage());
        }

        return vacations;
    }
}
