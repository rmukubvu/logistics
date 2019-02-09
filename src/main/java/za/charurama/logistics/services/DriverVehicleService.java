package za.charurama.logistics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.charurama.logistics.exceptions.RecordNotFoundException;
import za.charurama.logistics.models.*;
import za.charurama.logistics.repository.DriverRepository;
import za.charurama.logistics.repository.VehicleAllocationRepository;
import za.charurama.logistics.repository.VehicleRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public Driver saveDriver(Driver driver) {
        if (driver.getId() == null || driver.getId().isEmpty()) {
            driver.setId(null);
        }
        return driverRepository.save(driver);
    }

    public Vehicle saveVehicle(Vehicle vehicle){
        if (vehicle.getId() == null || vehicle.getId().isEmpty()) {
            vehicle.setId(null);

            String license = vehicle.getLicenseId().trim();
            try {
                Vehicle exists = getVehicleByLicense(license);
                if ( exists != null )
                    return exists;
            } catch (RecordNotFoundException e) {
                e.printStackTrace();
            }
        }
        return vehicleRepository.save(vehicle);
    }

    public VehicleAllocation saveVehicleAllocation(VehicleAllocation model){
        //first check if driver is allocated to a vehicle then remove him
        VehicleAllocation driverAllocation = vehicleAllocationRepository.findFirstByDriverIdEquals(model.getDriverId());
        if (driverAllocation != null) {
            driverAllocation.setAllocatedEndDate(new Date());
            vehicleAllocationRepository.save(driverAllocation);
        }
        //check if vehicle is allocated driver and swap out --> must come with clever way to pick if driver is in motion
        //remove vehicle
        //get previous allocation and close it
        VehicleAllocation update = vehicleAllocationRepository.findFirstByVehicleIdEqualsAndAllocatedEndDateIsNull(model.getVehicleId());
        if (update != null) {
            update.setAllocatedEndDate(new Date());
            vehicleAllocationRepository.save(update);
        }
        model.setAllocatedEndDate(null);
        return vehicleAllocationRepository.save(model);
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

    public Driver getDriverById(String driverId){
        Optional<Driver> optional = driverRepository.findById(driverId);
        if (optional.isPresent())
            return optional.get();
        return new Driver();
    }

    public Iterable<VehicleDriverViewModel> getVehicleDriverModelAllocations(){
        List<VehicleDriverViewModel> model = new ArrayList<>();
        Iterable<Vehicle> vehicles = vehicleRepository.findAll();
        for (Vehicle vehicle: vehicles
             ) {
            VehicleDriverViewModel viewModel = new VehicleDriverViewModel();
            VehicleAllocation allocation = getVehicleAllocationByVehicleId(vehicle.getId());
            if (allocation == null){
                viewModel.setDriverId("");
                viewModel.setFirstName("");
                viewModel.setLastName("");
                viewModel.setTelephone("");
                viewModel.setHasDriver(false);
            }else{
                Driver driver = getDriverById(allocation.getDriverId());
                if (driver!= null) {
                    viewModel.setDriverId(driver.getId());
                    viewModel.setFirstName(driver.getFirstName());
                    viewModel.setLastName(driver.getLastName());
                    viewModel.setTelephone(driver.getTelephone());
                    viewModel.setHasDriver(true);
                }
            }
            viewModel.setMake(vehicle.getMake());
            viewModel.setModel(vehicle.getModel());
            viewModel.setVehicleId(vehicle.getId());
            viewModel.setLicenseId(vehicle.getLicenseId());

            model.add(viewModel);
        }
        return model;
    }

    private Vehicle getVehicleByLicense(String license) throws RecordNotFoundException {
        Vehicle record = vehicleRepository.findFirstByLicenseIdEquals(license);
        if ( record != null ) {
            return record;
        }
        throw new RecordNotFoundException("Record is not available");
    }


}
