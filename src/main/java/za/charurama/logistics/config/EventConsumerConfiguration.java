package za.charurama.logistics.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import za.charurama.logistics.constants.RabbitQueue;
import za.charurama.logistics.consumer.EventConsumer;

//@Configuration
public class EventConsumerConfiguration {

    /*@Bean
    public TopicExchange exchange() {
        return new TopicExchange("eventExchange");
    }


    @Bean
    public Queue queue() {
        return new Queue(RabbitQueue.LOCATIONS_QUEUE_NAME);
    }


    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RabbitQueue.ROUTING_KEY);
    }

    @Bean
    public EventConsumer eventReceiver() {
        return new EventConsumer();
    }*/
}
