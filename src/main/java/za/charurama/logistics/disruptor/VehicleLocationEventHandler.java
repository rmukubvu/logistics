package za.charurama.logistics.disruptor;

import com.lmax.disruptor.EventHandler;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import za.charurama.logistics.constants.MessagingTypes;
import za.charurama.logistics.contracts.EmailService;
import za.charurama.logistics.contracts.SmsService;
import za.charurama.logistics.models.*;
import za.charurama.logistics.repository.MessagingLogsRepository;
import za.charurama.logistics.repository.SmsResponseRepository;
import za.charurama.logistics.services.ShipmentService;
import za.charurama.logistics.services.TrackingAnalyticsService;

import java.io.IOException;
import java.util.concurrent.Executors;


public class VehicleLocationEventHandler implements EventHandler<VehicleLocation> {
    @Autowired
    ShipmentService shipmentService;
    @Autowired
    MessagingLogsRepository messagingLogsRepository;

    @Autowired
    EmailService sendGridEmailService;

    @Autowired
    SmsService clickatellSmsService;

    @Autowired
    SmsResponseRepository smsResponseRepository;

    @Autowired
    TrackingAnalyticsService trackingAnalyticsService;

    @Override
    public void onEvent(VehicleLocation vehicleLocation, long l, boolean b) {
        try {
            //log it
            if (vehicleLocation != null) {
                shipmentService.logShipmentMovement(vehicleLocation);
                send(vehicleLocation.getVehicleId());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void send(String vehicleId) {
        Iterable<ShipmentViewModel> shipmentViewModels = shipmentService.getShipmentViewModel(vehicleId);
        for (ShipmentViewModel viewModel : shipmentViewModels
        ) {
            String analytics = getDistanceMetrics(viewModel.getManifestReference());
            String mapsUrl = String.format("http://www.google.com/maps/place/%f,%f", viewModel.getLatitude(), viewModel.getLongitude());
            String message = String.format("%s\n\n%s\n%s\n%s\n\n%s", mapsUrl, viewModel.getManifestReference(), viewModel.getConsignee(), viewModel.getStatus(),analytics);
            String subject = String.format("Status Update :- %s - %s", viewModel.getManifestReference(), viewModel.getConsignee());
            //mock data
            Executors.newSingleThreadExecutor().execute(() -> {
                for (ConsigneeContactDetails contact : viewModel.getContactDetails()
                ) {
                    String html = getHtml(viewModel,message);
                    sendEmailNotificationWithHtmlBody(vehicleId, contact.getEmailAddress(), subject, html);
                }
            });
        }
    }

    private String getDistanceMetrics(String manifestReference){
        DistanceMetrics distanceMetrics = trackingAnalyticsService.getDistanceMetricsByManifestReference(manifestReference);
        if (distanceMetrics != null){
            return String.format("Distance from Origin : %s\nDistance to destination : %s\nTotal distance : %s\nEstimated time of arrival : %s",
                    distanceMetrics.getDistanceFromOrigin(),
                    distanceMetrics.getDistanceToDestination(),
                    distanceMetrics.getCompleteDistanceToDestination(),
                    distanceMetrics.getEstimatedTimeOfArrival());
        }
        return "";
    }

    private String getHtml(ShipmentViewModel model,String message) {
        String result = "";
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream("static/emailstatus.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.replace("%coordinates%", String.format("%f,%f", model.getLatitude(),model.getLongitude()))
                .replace("%consignee%", model.getConsignee())
                .replace("%status%", message);
    }

    private void sendEmailNotificationWithHtmlBody(String userId,String destination,String subject,String body) {
        String response = sendGridEmailService.sendHTML("status@zimcon.co.za", destination, subject, body);
        logMessages(new MessagingLog(userId, destination, body, MessagingTypes.EMAIL, response));
    }

    private void logMessages(MessagingLog messagingLogs) {
        messagingLogsRepository.save(messagingLogs);
    }
}
