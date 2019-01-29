package za.charurama.logistics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.charurama.logistics.models.Consignee;
import za.charurama.logistics.models.ConsigneeContactDetails;
import za.charurama.logistics.models.Consignor;
import za.charurama.logistics.repository.ConsigneeContactsRepository;
import za.charurama.logistics.repository.ConsigneeRepository;
import za.charurama.logistics.repository.ConsignorRepository;

@Service
public class ConsignService {

    @Autowired
    private ConsigneeRepository consigneeRepository;

    @Autowired
    private ConsigneeContactsRepository consigneeContactsRepository;

    @Autowired
    private ConsignorRepository consignorRepository;


    public Consignor saveConsignor(Consignor consignor){
        return consignorRepository.save(consignor);
    }

    public Iterable<Consignor> getAllConsigors(){
        return consignorRepository.findAll();
    }

    public Consignee saveConsigee(Consignee consignee){
        return consigneeRepository.save(consignee);
    }

    public Consignee getConsigneeById(String consigneeId){
        return consigneeRepository.findById(consigneeId).get();
    }

    public ConsigneeContactDetails saveConsigneeContactDetails(ConsigneeContactDetails consigneeContactDetails){
        return consigneeContactsRepository.save(consigneeContactDetails);
    }

    public Iterable<ConsigneeContactDetails> getConsigneeContactDetailsById(String consigneeId){
        return consigneeContactsRepository.findAllConsigneeContactDetailsByConsigneeIdEquals(consigneeId);
    }

    public Iterable<Consignee> getAllConsignee(){
        return consigneeRepository.findAll();
    }
}
