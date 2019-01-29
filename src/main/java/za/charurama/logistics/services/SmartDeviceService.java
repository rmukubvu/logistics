package za.charurama.logistics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.charurama.logistics.models.SmartDevice;
import za.charurama.logistics.models.SmartDeviceAllocation;
import za.charurama.logistics.repository.SmartDeviceAllocationRepository;
import za.charurama.logistics.repository.SmartDeviceRepository;

@Service
public class SmartDeviceService {

    @Autowired
    private SmartDeviceRepository smartDeviceRepository;

    @Autowired
    private SmartDeviceAllocationRepository smartDeviceAllocationRepository;

    public SmartDevice saveSmartDevice(SmartDevice smartDevice){
        return smartDeviceRepository.save(smartDevice);
    }

    public SmartDeviceAllocation saveSmartDeviceAllocation(SmartDeviceAllocation smartDeviceAllocation){
        return smartDeviceAllocationRepository.save(smartDeviceAllocation);
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
}
