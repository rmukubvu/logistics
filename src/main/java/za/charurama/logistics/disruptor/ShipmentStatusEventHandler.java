package za.charurama.logistics.disruptor;

import com.lmax.disruptor.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import za.charurama.logistics.constants.MessagingTypes;
import za.charurama.logistics.contracts.EmailService;
import za.charurama.logistics.contracts.SmsService;
import za.charurama.logistics.messaging.TwilioClientSingleton;
import za.charurama.logistics.models.*;
import za.charurama.logistics.repository.MessagingLogsRepository;
import za.charurama.logistics.repository.SmsResponseRepository;
import za.charurama.logistics.services.ShipmentService;
import za.charurama.logistics.services.TrackingAnalyticsService;

import java.util.concurrent.Executors;


public class ShipmentStatusEventHandler implements EventHandler<ShipmentStatus> {
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

    @Value("${email.originator}")
    String from;

    @Value("${twilio.whatsapp.number}")
    String twilioNumber;

    /*
    private void send(ShipmentStatus event) {
        Iterable<ShipmentViewModel> shipmentViewModels = shipmentService.getShipmentViewModel(event.getVehicleId());
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
                    smsNotification(event.getVehicleId(), contact.getTelephone(), message);
                    sendWhatsappNotification(event.getVehicleId(), contact.getTelephone(), message);
                    sendEmailNotification(event.getVehicleId(), contact.getEmailAddress(), subject, message);
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

    private void sendEmailNotification(String vehicleId, String destination, String subject, String message) {
        String response = sendGridEmailService.sendText(from, destination, subject, message);
        logMessages(new MessagingLog(vehicleId, destination, message, MessagingTypes.EMAIL, response));
    }

    private void sendWhatsappNotification(String vehicleId, String destination, String message) {
        RoutingMessage routingMessage = new RoutingMessage(twilioNumber, destination, message, true);
        String response = TwilioClientSingleton.getInstance().send(routingMessage, true);
        logMessages(new MessagingLog(vehicleId, destination, message, MessagingTypes.WHATSAPP, response));
    }

    private void smsNotification(String vehicleId, String destination, String message) {
        SmsResponse response = clickatellSmsService.sendSms(destination, message);
        String responseFromServiceProvider;
        if (response.getError() == null) {
            responseFromServiceProvider = response.getMessages().get(0).getApiMessageId();
        } else {
            responseFromServiceProvider = response.getErrorDescription().toString();
        }
        //save response
        smsResponseRepository.save(response);
        //save logs
        logMessages(new MessagingLog(vehicleId, destination, message, MessagingTypes.SMS, responseFromServiceProvider));
    }

    private void logMessages(MessagingLog messagingLogs) {
        messagingLogsRepository.save(messagingLogs);
    } */

    @Override
    public void onEvent(ShipmentStatus shipmentStatus, long l, boolean b) {
        try {
            //log it
            shipmentService.saveShipmentStatus(shipmentStatus);
            //do processing
            //send(shipmentStatus);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
