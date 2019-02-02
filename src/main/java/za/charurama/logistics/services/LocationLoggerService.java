package za.charurama.logistics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.charurama.logistics.models.RestResponse;
import za.charurama.logistics.models.Vehicle;
import za.charurama.logistics.models.VehicleLocation;
import za.charurama.logistics.repository.VehicleLocationRepository;

import java.util.concurrent.Executors;


@Service
public class LocationLoggerService {
    @Autowired
    ShipmentService shipmentService;
    @Autowired
    VehicleLocationRepository vehicleLocationRepository;
    @Autowired
    CacheService cacheService;
    @Autowired
    DriverVehicleService driverVehicleService;

    public RestResponse log(VehicleLocation model) {
        //check if vehicle id is not attached and grab it
        if (model.getVehicleId() == null) {
            //get
            Vehicle vehicle = driverVehicleService.getVehicleByDeviceId(model.getDeviceId());
            if (vehicle != null)
                model.setVehicleId(vehicle.getId());
        }
        Executors.newSingleThreadExecutor().execute(() -> {
            //cache it and we always overwrite so as to get the freshest coordinate
            cacheService.saveLastKnownLocationOfTruck(model);
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

    public Iterable<VehicleLocation> replay(String vehicleId) {
        return vehicleLocationRepository.findAllByVehicleIdEquals(vehicleId);
    }

}
