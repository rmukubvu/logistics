package za.charurama.logistics.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.beans.factory.annotation.Autowired;
import za.charurama.logistics.models.ShipmentStatus;
import za.charurama.logistics.models.VehicleLocation;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DisruptorManager {

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private ShipmentStatusEventHandler handler = new ShipmentStatusEventHandler();
    private ShipmentStatusEventFactory factory = new ShipmentStatusEventFactory();
    private Disruptor<ShipmentStatus> disruptor = new Disruptor<>(factory, 1024, executorService);
    private RingBuffer<ShipmentStatus> ringBuffer;

    public DisruptorManager() {
        disruptor.handleEventsWith(handler);
        ringBuffer = disruptor.start();
    }

    public void publish(ShipmentStatus model){
        long sequence = ringBuffer.next();
        ShipmentStatus shipmentStatus = ringBuffer.get(sequence);
        shipmentStatus.setManifestReference(model.getManifestReference());
        shipmentStatus.setWayBillNumber(model.getWayBillNumber());
        shipmentStatus.setVehicleId(model.getVehicleId());
        shipmentStatus.setCreatedDate(new Date());
        shipmentStatus.setStatusId(model.getStatusId());
        ringBuffer.publish(sequence);
    }

    public void dispose(){
        System.out.println("disposing disruptor");
        disruptor.shutdown();
        executorService.shutdown();
    }

}
