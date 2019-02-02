package za.charurama.logistics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.charurama.logistics.cache.RedisCache;
import za.charurama.logistics.cache.RedisCacheClient;
import za.charurama.logistics.common.PhoneNumber;
import za.charurama.logistics.exceptions.RecordExistsException;
import za.charurama.logistics.models.Consignee;
import za.charurama.logistics.models.ConsigneeContactDetails;
import za.charurama.logistics.models.Consignor;
import za.charurama.logistics.models.RestResponse;
import za.charurama.logistics.repository.ConsigneeContactsRepository;
import za.charurama.logistics.repository.ConsigneeRepository;
import za.charurama.logistics.repository.ConsignorRepository;
import za.charurama.logistics.viewmodels.ConsigneeViewModel;

import java.util.Optional;

@Service
public class ConsignService {

    @Autowired
    ConsigneeRepository consigneeRepository;

    @Autowired
    ConsigneeContactsRepository consigneeContactsRepository;

    @Autowired
    ConsignorRepository consignorRepository;

    private RedisCache cacheClient = RedisCacheClient.getInstance().getCache();

    public RestResponse saveConsignor(Consignor consignor) {
        if (consignor.getId() == null || consignor.getId().isEmpty()) {
            consignor.setId(null);
            Consignor record = consignorRepository.findFirstByNameEquals(consignor.getName());
            if (record != null) {
                return new RestResponse(true,"Record already exists");
            }
        }
        consignorRepository.save(consignor);
        return new RestResponse(false,"Saved successfully");
    }

    public Iterable<Consignor> getAllConsigors(){
        return consignorRepository.findAll();
    }

    public RestResponse saveConsigee(Consignee consignee){
        if (consignee.getId() == null || consignee.getId().isEmpty()) {
            consignee.setId(null);
            Consignee record = consigneeRepository.findFirstByNameEquals(consignee.getName());
            if (record != null) {
                return new RestResponse(true,"Record already exists");
            }
        }
        consigneeRepository.save(consignee);
        return new RestResponse(false,"Saved successfully");
    }

    public Consignee getConsigneeById(String consigneeId){
        Optional<Consignee> optionalConsignee = consigneeRepository.findById(consigneeId);
        if (optionalConsignee.isPresent())
            return optionalConsignee.get();
        return null;
    }

    public RestResponse saveConsigneeContactDetails(ConsigneeContactDetails model){
        if (model.getId() == null || model.getId().isEmpty()) {
            model.setId(null);
            ConsigneeContactDetails record = consigneeContactsRepository.findFirstByTelephoneEquals(model.getTelephone());
            if (record != null) {
                return new RestResponse(true,"Record already exists");
            }
        }
        String msisdn = PhoneNumber.getCorrectPhoneNumber(model.getTelephone(),model.getCountryCode());
        model.setTelephone(msisdn);
        consigneeContactsRepository.save(model);
        cacheClient.setItem(model.getTelephone(),getConsigneeById(model.getConsigneeId()));
        return new RestResponse(false,"Saved successfully");
    }

    public Iterable<ConsigneeContactDetails> getConsigneeContactDetailsById(String consigneeId){
        return consigneeContactsRepository.findAllConsigneeContactDetailsByConsigneeIdEquals(consigneeId);
    }

    public Iterable<Consignee> getAllConsignee(){
        return consigneeRepository.findAll();
    }

    public Consignee getConsigneeByTelephoneNumber(String telephoneNumber) {
        return cacheClient.getItem(telephoneNumber, Consignee.class);
    }

    public RestResponse mapTelegramIdToConsignee(long telegramId,String telephoneNumber){
        Consignee consignee = getConsigneeByTelephoneNumber(telephoneNumber);
        if (consignee != null){
            cacheClient.setItem(String.valueOf(telegramId),consignee);
        }
        return new RestResponse(false,"Mapped successfully to " + consignee.getName());
    }

    public Consignee getConsigneeByTelegramId(long telegramId){
        return cacheClient.getItem(String.valueOf(telegramId),Consignee.class);
    }
}
