package za.charurama.logistics.models;

import lombok.Data;

@Data
public class RoutingMessage {
    private String sourceMsisdn;
    private String destinationMsisdn;
    private String message;
    private Boolean sendViaWhatsapp;

    public RoutingMessage(){}

    public RoutingMessage(String sourceMsisdn, String destinationMsisdn, String message, Boolean sendViaWhatsapp) {
        this.sourceMsisdn = sourceMsisdn;
        this.destinationMsisdn = destinationMsisdn;
        this.message = message;
        this.sendViaWhatsapp = sendViaWhatsapp;
    }
}
