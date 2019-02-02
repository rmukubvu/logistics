package za.charurama.logistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.charurama.logistics.models.Consignee;
import za.charurama.logistics.models.RestResponse;
import za.charurama.logistics.services.ConsignService;

@RestController
public class ClientQueryController {

    @Autowired
    ConsignService consignService;

    @GetMapping(value = "/mapClient", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse mapClient(@RequestParam("telegramId") long telegramId,@RequestParam("telephone") String telephone){
        return consignService.mapTelegramIdToConsignee(telegramId,telephone);
    }

    @GetMapping(value = "/consigneeByTelegram", produces = MediaType.APPLICATION_JSON_VALUE)
    public Consignee getConsigneeByTelegramId(@RequestParam("telegramId") long telegramId){
        return consignService.getConsigneeByTelegramId(telegramId);
    }

}
