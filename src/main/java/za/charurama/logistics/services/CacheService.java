package za.charurama.logistics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.charurama.logistics.cache.RedisCache;
import za.charurama.logistics.cache.RedisCacheClient;
import za.charurama.logistics.constants.CacheKeys;
import za.charurama.logistics.models.ClearingStatus;
import za.charurama.logistics.models.Country;
import za.charurama.logistics.models.VehicleLocation;
import za.charurama.logistics.repository.ClearingStatusRepository;
import za.charurama.logistics.repository.CountryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CacheService {

    @Autowired
    private ClearingStatusRepository clearingStatusRepository;

    @Autowired
    private CountryRepository countryRepository;
    private RedisCache cacheClient = RedisCacheClient.getInstance().getCache();

    public void cacheLookupData(){
        Iterable<ClearingStatus> clearingStatuses = clearingStatusRepository.findAll();
        Iterable<Country> countries = countryRepository.findAll();
        //do for clearing statuses
        cacheClient.setItem(CacheKeys.CLEARING_STATUS,clearingStatuses);
        //do for countries
        cacheClient.setItem(CacheKeys.COUNTRIES,countries);
    }

    public Iterable<ClearingStatus> getClearingStatus(){
        return cacheClient.getItemsAsList(CacheKeys.CLEARING_STATUS,ClearingStatus[].class);
    }

    public Iterable<Country> getCountries(){
        return cacheClient.getItemsAsList(CacheKeys.COUNTRIES,Country[].class);
    }

    public ClearingStatus getClearingStatusById(int id) {
        Iterable<ClearingStatus> clearingStatuses = cacheClient.getItemsAsList(CacheKeys.CLEARING_STATUS, ClearingStatus[].class);
        Optional<ClearingStatus> optional = ((List<ClearingStatus>) clearingStatuses).stream().filter(e -> e.getStatusId() == id).findFirst();
        return optional.get();
    }

    public void saveLastKnownLocationOfTruck(VehicleLocation model) {
        cacheClient.setItem(model.getVehicleId(),model);
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
