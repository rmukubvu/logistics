package za.charurama.logistics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.charurama.logistics.cache.RedisCache;
import za.charurama.logistics.cache.RedisCacheClient;
import za.charurama.logistics.constants.CacheKeys;
import za.charurama.logistics.models.*;
import za.charurama.logistics.repository.ClearingStatusRepository;
import za.charurama.logistics.repository.CountryRepository;
import za.charurama.logistics.repository.SmartDeviceAllocationRepository;
import za.charurama.logistics.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CacheService {

    @Autowired
    ClearingStatusRepository clearingStatusRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    SmartDeviceAllocationRepository smartDeviceAllocationRepository;

    private RedisCache cacheClient = RedisCacheClient.getInstance().getCache();

    public void cacheData(){
        Iterable<ClearingStatus> clearingStatuses = clearingStatusRepository.findAll();
        Iterable<Country> countries = countryRepository.findAll();
        Iterable<User> users = userRepository.findAll();
        Iterable<SmartDeviceAllocation> smartDevices = smartDeviceAllocationRepository.findAll();
        //do for clearing statuses
        cacheClient.setItem(CacheKeys.CLEARING_STATUS,clearingStatuses);
        //do for countries
        cacheClient.setItem(CacheKeys.COUNTRIES,countries);
        //cache users
        for (User user: users
             ) {
            cacheClient.setItem(user.getEmailAddress(),user);
        }

        for (SmartDeviceAllocation smart: smartDevices
             ) {
            cacheClient.setItem(String.format("ALLOCATED_DEVICE_%s",smart.getVehicleId()),smart);
        }
    }

    /*public Iterable<ClearingStatus> getClearingStatus(){
        return cacheClient.getItemsAsList(CacheKeys.CLEARING_STATUS,ClearingStatus[].class);
    }

    public Iterable<Country> getCountries(){
        return cacheClient.getItemsAsList(CacheKeys.COUNTRIES,Country[].class);
    }*/

    public ClearingStatus getClearingStatusById(int id) {
        Iterable<ClearingStatus> clearingStatuses = cacheClient.getItemsAsList(CacheKeys.CLEARING_STATUS, ClearingStatus[].class);
        Optional<ClearingStatus> optional = ((List<ClearingStatus>) clearingStatuses).stream().filter(e -> e.getStatusId() == id).findFirst();
        return optional.get();
    }

    public String getDeviceIdFromVehicleId(String vehicleId){
        SmartDeviceAllocation cached = cacheClient.getItem(String.format("ALLOCATED_DEVICE_%s",vehicleId),SmartDeviceAllocation.class);
        if (cached != null)
            return cached.getDeviceId();
        return null;
    }

    public void saveLastKnownLocationOfTruck(VehicleLocation model) {
        cacheClient.setItem(model.getVehicleId(),model);
    }

    public void saveOffloadedShipment(Shipment model){
        cacheClient.setItem(String.format("SHIPMENT_%d",model.getWayBillNumber()),model);
    }

    public Shipment getOffloadedShipmentByWaybill(long waybill){
        return cacheClient.getItem(String.format("SHIPMENT_%d",waybill),Shipment.class);
    }

    public VehicleLocation getLastKnownLocation(String vehicleId) {
        return cacheClient.getItem(vehicleId,VehicleLocation.class);
    }

    public void saveLastKnownLocationByWaybill(long waybill,VehicleLocation model) {
        cacheClient.setItem(String.valueOf(waybill), model);
    }

    public VehicleLocation getLastKnownLocationByWaybill(long waybill){
        return cacheClient.getItem(String.valueOf(waybill),VehicleLocation.class);
    }

}
