import it.unifi.dinfo.rulesengine.amqp.SenderService;
import it.unifi.dinfo.rulesengine.rulesengines.daily.prato.HolidayShutdown;
import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestHolidayShutdown {
    RulesEngine rulesEngine;

    @Autowired
    SenderService senderService;

    @Before
    public void before(){
        rulesEngine = RulesEngineBuilder.aNewRulesEngine().withSilentMode(true).build();
    }

    @Test
    public void test(){
        HolidayShutdown holidayShutdown  = new HolidayShutdown();
        rulesEngine.registerRule(holidayShutdown);
        rulesEngine.fireRules();
    }
}
