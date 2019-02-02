package za.charurama.logistics.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import za.charurama.logistics.models.VehicleLocation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DisruptorManager {

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private VehicleLocationEventHandler handler = new VehicleLocationEventHandler();
    private VehicleLocationEventFactory factory = new VehicleLocationEventFactory();
    private Disruptor<VehicleLocation> disruptor = new Disruptor<>(factory, 1024, executorService);
    private RingBuffer<VehicleLocation> ringBuffer;

    public DisruptorManager() {
        disruptor.handleEventsWith(handler);
        ringBuffer = disruptor.start();
    }

    public void publish(VehicleLocation model){
        long sequence = ringBuffer.next();
        VehicleLocation vehicleLocation = ringBuffer.get(sequence);
        vehicleLocation.setLongitude(model.getLongitude());
        vehicleLocation.setLatitude(model.getLatitude());
        vehicleLocation.setLocationDateTime(model.getLocationDateTime());
        vehicleLocation.setVehicleId(model.getVehicleId());
        vehicleLocation.setDeviceId(model.getDeviceId());
        ringBuffer.publish(sequence);
    }

    public void dispose(){
        System.out.println("disposing disruptor");
        disruptor.shutdown();
        executorService.shutdown();
    }

}
