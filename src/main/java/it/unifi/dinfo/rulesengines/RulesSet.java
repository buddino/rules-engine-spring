package it.unifi.dinfo.rulesengines;

import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;

public abstract class RulesSet {
    protected RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine().withSilentMode(true).build();
    public void fireRules(){
        rulesEngine.fireRules();
    }
}
