package za.charurama.logistics.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "shipment")
public class Shipment {
    @Id
    private String id;
    @Indexed
    private String vehicleId;
    @Indexed
    private String manifestReference;
    @Indexed
    private long wayBillNumber;
    @Indexed
    private String consigneeId;
    private double sourceLatitude;
    private double sourceLongitude;
    private double destinationLatitude;
    private double destinationLongitude;
    private Date loadedDate;
}
