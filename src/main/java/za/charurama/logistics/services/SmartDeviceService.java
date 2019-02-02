package za.charurama.logistics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.charurama.logistics.models.RestResponse;
import za.charurama.logistics.models.SmartDevice;
import za.charurama.logistics.models.SmartDeviceAllocation;
import za.charurama.logistics.models.Vehicle;
import za.charurama.logistics.repository.SmartDeviceAllocationRepository;
import za.charurama.logistics.repository.SmartDeviceRepository;
import za.charurama.logistics.repository.VehicleRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class SmartDeviceService {

    @Autowired
    SmartDeviceRepository smartDeviceRepository;
    @Autowired
    SmartDeviceAllocationRepository smartDeviceAllocationRepository;
    @Autowired
    VehicleRepository vehicleRepository;

    public SmartDevice saveSmartDevice(SmartDevice smartDevice){
        return smartDeviceRepository.save(smartDevice);
    }

    public RestResponse saveSmartDeviceAllocation(SmartDeviceAllocation smartDeviceAllocation){
        SmartDeviceAllocation record = getDeviceAllocationByDeviceId(smartDeviceAllocation.getDeviceId());
        if ( record != null ){
            Optional<Vehicle> optionalVehicle = vehicleRepository.findById(record.getVehicleId());
            if (optionalVehicle.isPresent()){
                Vehicle vehicle = optionalVehicle.get();
                return new RestResponse(true,String.format("This device is already allocated to %s with license %s",vehicle.getMake(),vehicle.getLicenseId()));
            }
        }
        smartDeviceAllocationRepository.save(smartDeviceAllocation);
        return new RestResponse(false,"Device attached to device");
    }

    public Iterable<SmartDevice> getSmartDevices(){
        return smartDeviceRepository.findAll();
    }

    public SmartDeviceAllocation getDeviceAllocationByVehicle(String vehicleId){
        return smartDeviceAllocationRepository.findSmartDeviceAllocationByVehicleIdEquals(vehicleId);
    }

    public SmartDeviceAllocation getDeviceAllocationByDeviceId(String deviceId){
        return smartDeviceAllocationRepository.findSmartDeviceAllocationByDeviceIdEquals(deviceId);
    }

    public Iterable<SmartDeviceAllocation> getSmartDeviceAllocation() {
        return smartDeviceAllocationRepository.findAll();
    }

    public RestResponse unallocatedDeviceFromVehicle(String deviceId){
        SmartDeviceAllocation smartDeviceAllocation = smartDeviceAllocationRepository.findSmartDeviceAllocationByDeviceIdEquals(deviceId);
        smartDeviceAllocationRepository.delete(smartDeviceAllocation);
        return new RestResponse(false,"Device has been detached from vehicle");
    }
}
