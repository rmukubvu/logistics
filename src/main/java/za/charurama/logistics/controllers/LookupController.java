package za.charurama.logistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import za.charurama.logistics.models.ClearingStatus;
import za.charurama.logistics.models.Country;
import za.charurama.logistics.services.LookupService;


@RestController
public class LookupController {

    @Autowired
    private LookupService lookupService;

    @PostMapping(
            value = "/clearingstatus",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ClearingStatus newClearingStatus(@RequestBody ClearingStatus clearingStatus) {
        return lookupService.saveClearingStatus(clearingStatus);
    }

    @GetMapping(
            value = "/clearingstatus",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Iterable<ClearingStatus> getAllClearingStatuses() {
        return lookupService.getClearingStatuses();
    }

    @PostMapping(
            value = "/countries",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Country addNewCountry(@RequestBody Country country) {
        return lookupService.saveCountry(country);
    }

    @GetMapping(
            value = "/countries",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Iterable<Country> getCountries() {
        return lookupService.getCountries();
    }

}
