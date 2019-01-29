package za.charurama.logistics.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import za.charurama.logistics.constants.RabbitQueue;
import za.charurama.logistics.disruptor.DisruptorManager;
import za.charurama.logistics.models.VehicleLocation;

public class NotificationProcessorService {

    /*private final RabbitTemplate rabbitTemplate;
    private final Exchange exchange;
    private ObjectMapper mapper;

    public NotificationProcessorService(RabbitTemplate rabbitTemplate, Exchange exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        mapper = new ObjectMapper();
    }

    public void pushEventToQueueForProcessing(VehicleLocation data){
        try {
            String payLoad = mapper.writeValueAsString(data);
            rabbitTemplate.convertAndSend(exchange.getName(), RabbitQueue.ROUTING_KEY, payLoad);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }*/

}
