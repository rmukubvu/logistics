package za.charurama.logistics.disruptor;

import com.lmax.disruptor.EventFactory;
import za.charurama.logistics.models.ShipmentStatus;
import za.charurama.logistics.models.VehicleLocation;


public class VehicleLocationEventFactory implements EventFactory<VehicleLocation> {
    @Override
    public VehicleLocation newInstance() {
        return new VehicleLocation();
    }
}
