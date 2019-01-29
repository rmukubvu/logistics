package za.charurama.logistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import za.charurama.logistics.models.RestResponse;
import za.charurama.logistics.models.ShipmentStatus;
import za.charurama.logistics.services.NotificationsService;

@RestController
public class NotificationController {

    @Autowired
    private NotificationsService notificationsService;

    @PostMapping(
            value = "/notification",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse statusChange(@RequestBody ShipmentStatus shipmentStatus){
        return notificationsService.sendNotification(shipmentStatus);
    }
}
