package it.unifi.dinfo.rulesengine.helper;

import org.joda.time.Interval;

import java.util.Date;

public class Vacation {
    private Date start;
    private Date end;
    private String name;
    private String info;

    public Vacation(){}
    public Vacation(Date start, Date end, String name){
        this.start = start;
        this.end = end;
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public long getDurationInDays(){
        try {
            Interval interval = new Interval(start.getTime(), end.getTime());
            return interval.toDuration().getStandardDays();
        }
        catch (IllegalArgumentException e){
            return 0;
        }
    }

    public long getDaysBetween(Date today) {
        if (start.after(today))
            return 0;
        try {
            Interval interval = new Interval(today.getTime(), start.getTime());
            return interval.toDuration().getStandardDays();

        } catch (IllegalArgumentException e) {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Vacation{" +
                "start=" + start +
                ", end=" + end +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
