package za.charurama.logistics.services;

import za.charurama.logistics.disruptor.DisruptorManager;
import za.charurama.logistics.models.ShipmentStatus;


public class LocationProcessorService {

    private final DisruptorManager disruptorManager;

    public LocationProcessorService(DisruptorManager disruptorManager) {
        this.disruptorManager = disruptorManager;
    }

    public void push(ShipmentStatus status){
        this.disruptorManager.publish(status);
    }
}
