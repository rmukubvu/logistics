package za.charurama.logistics.controllers;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import za.charurama.logistics.models.Country;
import za.charurama.logistics.models.RestResponse;
import za.charurama.logistics.services.RealtimeService;

@RestController
public class RealtimeController {

    @Autowired
    RealtimeService realtimeService;

    @PostMapping(
            value = "/mqtt",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse saveLocation(@RequestBody Country country){
        try {
            realtimeService.sendMessage(country.getCountryName());
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return new RestResponse("Message sent");
    }
}
