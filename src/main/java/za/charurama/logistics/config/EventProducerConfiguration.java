package za.charurama.logistics.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import za.charurama.logistics.services.NotificationProcessorService;

//@Configuration
public class EventProducerConfiguration {

   /* @Bean
    public NotificationProcessorService customerService(RabbitTemplate rabbitTemplate, Exchange senderTopicExchange) {
        return new NotificationProcessorService(rabbitTemplate, senderTopicExchange);
    }*/
}
