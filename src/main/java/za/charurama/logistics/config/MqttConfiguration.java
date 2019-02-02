package za.charurama.logistics.config;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import za.charurama.logistics.services.RealtimeService;

import java.util.UUID;


@Configuration
public class MqttConfiguration {

    @Bean
    public IMqttClient mqttClientFactory() throws MqttException {
        String publisherId = UUID.randomUUID().toString();
        return new MqttClient("ws://localhost:8083/mqtt",publisherId);
    }

    @Bean
    public RealtimeService realtimeService(IMqttClient mqttClient){
        return new RealtimeService(mqttClient);
    }
}
