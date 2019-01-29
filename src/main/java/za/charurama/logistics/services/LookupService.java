package za.charurama.logistics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.charurama.logistics.cache.RedisCache;
import za.charurama.logistics.cache.RedisCacheClient;
import za.charurama.logistics.constants.CacheKeys;
import za.charurama.logistics.models.ClearingStatus;
import za.charurama.logistics.models.Country;
import za.charurama.logistics.repository.ClearingStatusRepository;
import za.charurama.logistics.repository.CountryRepository;

@Service
public class LookupService {

    @Autowired
    private ClearingStatusRepository clearingStatusRepository;

    @Autowired
    private CountryRepository countryRepository;

    public ClearingStatus saveClearingStatus(ClearingStatus clearingStatus){
        return clearingStatusRepository.save(clearingStatus);
    }

    public Iterable<ClearingStatus> getClearingStatuses(){
        RedisCache cacheClient = RedisCacheClient.getInstance().getCache();
        Iterable<ClearingStatus> cachesStatues = cacheClient.getItemsAsList(CacheKeys.CLEARING_STATUS,ClearingStatus[].class);
        if (cachesStatues != null)
            return cachesStatues;
        return clearingStatusRepository.findAll();
    }

    public Country saveCountry(Country country){
        return countryRepository.save(country);
    }

    public Iterable<Country> getCountries(){
        RedisCache cacheClient = RedisCacheClient.getInstance().getCache();
        Iterable<Country> cachesStatues = cacheClient.getItemsAsList(CacheKeys.COUNTRIES,Country[].class);
        if (cachesStatues != null)
            return cachesStatues;
        return countryRepository.findAll();
    }
}
