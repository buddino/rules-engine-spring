package it.unifi.dinfo.rulesengine.counter;

import java.util.ArrayList;
import java.util.List;

public class CounterSet {
    List<Counter> counterList = new ArrayList<>();

    public void add(Counter u) {
        counterList.add(u);
    }

    public void remove(Counter u) {
        counterList.remove(u);
    }

    public void update(){
        counterList.forEach(Counter::update);
    }

}
