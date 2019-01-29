package za.charurama.logistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.charurama.logistics.models.DistanceMetrics;
import za.charurama.logistics.services.TrackingAnalyticsService;

@RestController
public class AnalyticsController {
    @Autowired
    TrackingAnalyticsService trackingAnalyticsService;

    @GetMapping(value = "/analytics",produces = MediaType.APPLICATION_JSON_VALUE)
    public DistanceMetrics getDistanceAnalytics(@RequestParam("waybill") long waybill){
        return trackingAnalyticsService.getDistanceMetrics(waybill);
    }
}
