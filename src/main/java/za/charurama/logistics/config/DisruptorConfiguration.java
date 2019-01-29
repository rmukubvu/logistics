package za.charurama.logistics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import za.charurama.logistics.disruptor.DisruptorManager;
import za.charurama.logistics.services.LocationProcessorService;

@Configuration
public class DisruptorConfiguration {

    @Bean
    public DisruptorManager disruptorManager(){
        return new DisruptorManager();
    }

    @Bean
    public LocationProcessorService processorService(DisruptorManager disruptorManager){
        return new LocationProcessorService(disruptorManager);
    }

}
