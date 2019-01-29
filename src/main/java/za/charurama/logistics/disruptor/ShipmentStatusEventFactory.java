package za.charurama.logistics.disruptor;

import com.lmax.disruptor.EventFactory;
import za.charurama.logistics.models.ShipmentStatus;


public class ShipmentStatusEventFactory implements EventFactory<ShipmentStatus> {
    @Override
    public ShipmentStatus newInstance() {
        return new ShipmentStatus();
    }
}
