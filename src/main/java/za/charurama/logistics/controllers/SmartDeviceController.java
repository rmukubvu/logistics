package za.charurama.logistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import za.charurama.logistics.models.RestResponse;
import za.charurama.logistics.models.SmartDevice;
import za.charurama.logistics.models.SmartDeviceAllocation;
import za.charurama.logistics.services.SmartDeviceService;

@RestController
public class SmartDeviceController {
    @Autowired
    private SmartDeviceService smartDeviceService;

    @RequestMapping(value = "/smartdevice",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public SmartDevice saveSmartDevice(@RequestBody SmartDevice smartDevice){
        return smartDeviceService.saveSmartDevice(smartDevice);
    }

    @RequestMapping(value = "/smartdevice",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<SmartDevice> getAllSmartDevices(){
        return smartDeviceService.getSmartDevices();
    }

    @RequestMapping(value = "/smartdevice/allocation",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse saveSmartDeviceAllocation(@RequestBody SmartDeviceAllocation smartDeviceAllocation){
        return smartDeviceService.saveSmartDeviceAllocation(smartDeviceAllocation);
    }

    @RequestMapping(value = "/smartdevice/allocation",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<SmartDeviceAllocation> getSmartDeviceAllocation(){
        return smartDeviceService.getSmartDeviceAllocation();
    }

    @RequestMapping(value = "/smartdevice/vehicle/",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public SmartDeviceAllocation getAllocationByVehicle(@RequestParam("vehicleId") String vehicleId){
        return smartDeviceService.getDeviceAllocationByVehicle(vehicleId);
    }

    @RequestMapping(value = "/smartdevice/device/",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public SmartDeviceAllocation getAllocationByDevice(@RequestParam("deviceId") String deviceId){
        return smartDeviceService.getDeviceAllocationByDeviceId(deviceId);
    }

    @RequestMapping(value = "/smartdevice/unAllocate",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse unallocatedDeviceFromVehicle(@RequestParam("deviceId") String deviceId){
        return smartDeviceService.unallocatedDeviceFromVehicle(deviceId);
    }

}
