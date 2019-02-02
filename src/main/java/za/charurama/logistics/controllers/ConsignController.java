package za.charurama.logistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import za.charurama.logistics.models.Consignee;
import za.charurama.logistics.models.ConsigneeContactDetails;
import za.charurama.logistics.models.Consignor;
import za.charurama.logistics.models.RestResponse;
import za.charurama.logistics.services.ConsignService;

@RestController
public class ConsignController {
    @Autowired
    private ConsignService consigneeService;


    @PostMapping(value = "/consignor", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse saveConsignor(@RequestBody Consignor consignor) {
        return consigneeService.saveConsignor(consignor);
    }

    @GetMapping(value = "/consignor", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Consignor> getAllConsigors() {
        return consigneeService.getAllConsigors();
    }

    @PostMapping(value = "/consignee", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse saveConsigee(@RequestBody Consignee consignee) {
        return consigneeService.saveConsigee(consignee);
    }

    @GetMapping(value = "/consignee", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Consignee> getAllConsignee() {
        return consigneeService.getAllConsignee();
    }

    @PostMapping(value = "/consignee/contacts", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse saveConsigeeContacts(@RequestBody ConsigneeContactDetails consigneeContactDetails) {
        return consigneeService.saveConsigneeContactDetails(consigneeContactDetails);
    }

    @GetMapping(value = "/consignee/contacts", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<ConsigneeContactDetails> getAllConsignee(@RequestParam("id") String id) {
        return consigneeService.getConsigneeContactDetailsById(id);
    }

    @GetMapping(value = "/consignee/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public Consignee getConsigneeById(@RequestParam("id") String id) {
        return consigneeService.getConsigneeById(id);
    }

}
