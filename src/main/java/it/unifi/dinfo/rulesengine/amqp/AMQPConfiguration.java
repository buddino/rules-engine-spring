package it.unifi.dinfo.rulesengine.amqp;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfiguration {
    //TODO Externalize
    String rabbitServer = "broker.sparkworks.net";
    String rabbitPort = "5672";
    String sparkworksClient = "NOTIFICATION";
    String sparkworksSecret = "63d0acd9-f477-4ab2-a27a-7332224877e8";

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitServer);
        connectionFactory.setPort(Integer.parseInt(rabbitPort));
        connectionFactory.setUsername(sparkworksClient);
        connectionFactory.setPassword(sparkworksSecret);
        return connectionFactory;
    }

}
