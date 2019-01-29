package za.charurama.logistics.models;

import lombok.Data;

@Data
public class ShipmentViewModel {
    private String consignee;
    private String manifestReference;
    private double latitude;
    private double longitude;
    private String status;
    private long wayBill;
    private Iterable<ConsigneeContactDetails> contactDetails;

    public ShipmentViewModel(String consignee, String status, String manifestReference, double latitude, double longitude, long waybill, Iterable<ConsigneeContactDetails> contactDetails) {
        this.consignee = consignee;
        this.manifestReference = manifestReference;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contactDetails = contactDetails;
        this.status = status;
        this.wayBill = waybill;
    }

}
