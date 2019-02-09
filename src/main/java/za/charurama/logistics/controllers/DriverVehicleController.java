package za.charurama.logistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import za.charurama.logistics.models.Driver;
import za.charurama.logistics.models.Vehicle;
import za.charurama.logistics.models.VehicleAllocation;
import za.charurama.logistics.models.VehicleDriverViewModel;
import za.charurama.logistics.services.DriverVehicleService;

@RestController
public class DriverVehicleController {
    @Autowired
    private DriverVehicleService driverVehicleService;

    @RequestMapping(value = "/driver",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public Driver saveDriver(@RequestBody Driver driver){
        return driverVehicleService.saveDriver(driver);
    }

    @RequestMapping(value = "/driver/id" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Driver getDriverById(@RequestParam("driverId")String driverId){
        return driverVehicleService.getDriverById(driverId);
    }

    @RequestMapping(value = "/driver" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Driver> getAllDrivers(){
        return driverVehicleService.getAllDrivers();
    }

    @RequestMapping(value = "/vehicle",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public Vehicle saveVehicle(@RequestBody Vehicle vehicle){
        return driverVehicleService.saveVehicle(vehicle);
    }

    @RequestMapping(value = "/vehicle/driver/allocation",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public VehicleAllocation saveVehicleDriverAllocation(@RequestBody VehicleAllocation vehicleAllocation){
        return driverVehicleService.saveVehicleAllocation(vehicleAllocation);
    }

    @RequestMapping(value = "/vehicle" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Vehicle> getAllVehicles(){
        return driverVehicleService.getAllVehicles();
    }

    @RequestMapping(value = "/vehicle/id" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Vehicle getVehicleById(@RequestParam("vehicleId") String vehicleId){
        return driverVehicleService.getVehicleById(vehicleId);
    }

    @RequestMapping(value = "/vehicleDriverModel" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<VehicleDriverViewModel> getVehicleDriverModelAllocations(){
        return driverVehicleService.getVehicleDriverModelAllocations();
    }

    @RequestMapping(value = "/vehicle/device/id" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Vehicle getVehicleByDeviceId(@RequestParam("deviceId") String deviceId){
        return driverVehicleService.getVehicleByDeviceId(deviceId);
    }

    @RequestMapping(value = "/vehicle/driver/history" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<VehicleAllocation> getVehicleAllocationHistoryByDriver(@RequestParam("driverId") String driverId){
        return driverVehicleService.getVehicleAllocationHistoryByDriver(driverId);
    }

    @RequestMapping(value = "/vehicle/driver/allocation" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public VehicleAllocation getVehicleAllocationByVehicle(@RequestParam("vehicleId") String vehicleId){
        return driverVehicleService.getVehicleAllocationByVehicleId(vehicleId);
    }
}
