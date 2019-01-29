package za.charurama.logistics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.charurama.logistics.models.Driver;
import za.charurama.logistics.models.SmartDeviceAllocation;
import za.charurama.logistics.models.Vehicle;
import za.charurama.logistics.models.VehicleAllocation;
import za.charurama.logistics.repository.DriverRepository;
import za.charurama.logistics.repository.VehicleAllocationRepository;
import za.charurama.logistics.repository.VehicleRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class DriverVehicleService {

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    VehicleAllocationRepository vehicleAllocationRepository;

    @Autowired
    SmartDeviceService smartDeviceService;

    public Driver saveDriver(Driver driver){
        return driverRepository.save(driver);
    }

    public Vehicle saveVehicle(Vehicle vehicle){
        return vehicleRepository.save(vehicle);
    }

    public VehicleAllocation saveVehicleAllocation(VehicleAllocation vehicleAllocation){
        //get previous allocation and close it
        VehicleAllocation update = vehicleAllocationRepository
                .findFirstByVehicleIdEqualsAndAllocatedEndDateIsNull(vehicleAllocation.getVehicleId());
        if (update != null) {
            update.setAllocatedEndDate(new Date());
            vehicleAllocationRepository.save(update);
        }
        //vehicleAllocationRepository - insert
        vehicleAllocation.setAllocatedEndDate(null);
        return vehicleAllocationRepository.save(vehicleAllocation);
    }

    public Iterable<Driver> getAllDrivers(){
        return driverRepository.findAll();
    }

    //to be indexed by lucene for autocomplete
    public Iterable<Vehicle> getAllVehicles(){
        return vehicleRepository.findAll();
    }

    public Iterable<VehicleAllocation> getVehicleAllocationHistoryByDriver(String driverId){
        return vehicleAllocationRepository.findAllByDriverIdEquals(driverId);
    }

    public VehicleAllocation getVehicleAllocationByVehicleId(String vehicleId){
        return vehicleAllocationRepository.findFirstByVehicleIdEqualsAndAllocatedEndDateIsNull(vehicleId);
    }

    public Vehicle getVehicleById(String vehicleId) {
        Optional<Vehicle> optional = vehicleRepository.findById(vehicleId);
        if (optional.isPresent())
            return optional.get();
        return new Vehicle();
    }

    public Vehicle getVehicleByDeviceId(String deviceId){
        SmartDeviceAllocation smartDeviceAllocation = smartDeviceService.getDeviceAllocationByDeviceId(deviceId);
        if (smartDeviceAllocation != null ){
            return getVehicleById(smartDeviceAllocation.getVehicleId());
        }
        return new Vehicle();
    }
}
