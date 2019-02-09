package za.charurama.logistics.services;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.charurama.logistics.models.RestResponse;
import za.charurama.logistics.models.Vehicle;
import za.charurama.logistics.models.VehicleLocation;
import za.charurama.logistics.repository.VehicleLocationRepository;
import za.charurama.logistics.repository.VehicleRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;


@Service
public class LocationLoggerService extends Notify {
    @Autowired
    ShipmentService shipmentService;
    @Autowired
    VehicleLocationRepository vehicleLocationRepository;
    @Autowired
    CacheService cacheService;
    @Autowired
    DriverVehicleService driverVehicleService;
    @Autowired
    RealtimeService realtimeService;
    @Autowired
    VehicleRepository vehicleRepository;

    public RestResponse log(VehicleLocation model) {
        //check if vehicle id is not attached and grab it
        if (model.getVehicleId() == null) {
            //get
            Vehicle vehicle = driverVehicleService.getVehicleByDeviceId(model.getDeviceId());
            if (vehicle != null)
                model.setVehicleId(vehicle.getId());
        }
        Executors.newSingleThreadExecutor().execute(() -> {
            NotifyAlways(String.format("Device %s has updated its location",model.getDeviceId()));
            //cache it and we always overwrite so as to get the freshest coordinate
            cacheService.saveLastKnownLocationOfTruck(model);
            //send message
            updateMap(String.format("%s|%f|%f",model.getVehicleId(),model.getLatitude(),model.getLongitude()));
            //save to db
            vehicleLocationRepository.save(model);
            //do movements update
            //processorService.push(model);
            shipmentService.logShipmentMovement(model);
        });
        return new RestResponse("Queued");
    }

    public VehicleLocation getLastKnownLocation(String vehicleId) {
        return cacheService.getLastKnownLocation(vehicleId);
    }

    public void addLastKnowLocation(String vehicleId,double latitude,double longitude){
        VehicleLocation vehicleLocation = new VehicleLocation();
        vehicleLocation.setVehicleId(vehicleId);
        vehicleLocation.setLocationDateTime(new Date());
        vehicleLocation.setLatitude((float) latitude);
        vehicleLocation.setDeviceId(cacheService.getDeviceIdFromVehicleId(vehicleId));
        vehicleLocation.setLongitude((float) longitude);
        cacheService.saveLastKnownLocationOfTruck(vehicleLocation);
    }

    public Iterable<VehicleLocation> replay(String vehicleId) {
        return vehicleLocationRepository.findAllByVehicleIdEquals(vehicleId);
    }

    public Iterable<VehicleLocation> getCurrentLocationOfVehicles(){
        List<VehicleLocation> locations = new ArrayList<>();
        Iterable<Vehicle> vehicles = vehicleRepository.findAll();
        for (Vehicle vehicle:vehicles
        ) {
            VehicleLocation lastKnownLocation = getLastKnownLocation(vehicle.getId());
            if (lastKnownLocation != null)
                locations.add(lastKnownLocation);
        }
        return locations;
    }

    @Override
    public void NotifyAlways(String message) {
        try {
            realtimeService.sendMessage(message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void updateMap(String message){
        try {
            realtimeService.locationUpdates(message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
