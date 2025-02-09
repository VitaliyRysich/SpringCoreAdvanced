package guru.springframework.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;


@Configuration
public class JMSConfig {

    public static final String textMessage = "text.messagequeue";

    @Bean
    public Queue textMessageQueue(){
        return new ActiveMQQueue(textMessage);
    }
}
