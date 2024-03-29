package za.charurama.logistics.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import za.charurama.logistics.models.RestResponse;
import za.charurama.logistics.models.VehicleLocation;
import za.charurama.logistics.services.LocationLoggerService;

@RestController
public class LocationController {

    @Autowired
    LocationLoggerService locationLoggerService;

    @PostMapping(
            value = "/location",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse saveLocation(@RequestBody VehicleLocation vehicleLocation){
       return locationLoggerService.log(vehicleLocation);
    }

    @GetMapping(value = "/replaytrip",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<VehicleLocation> replay(@RequestParam("vehicleId") String vehicleId){
        return locationLoggerService.replay(vehicleId);
    }

    @GetMapping(value = "/vehicleLocations",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<VehicleLocation> vehicleLocations(){
        return locationLoggerService.getCurrentLocationOfVehicles();
    }
}
