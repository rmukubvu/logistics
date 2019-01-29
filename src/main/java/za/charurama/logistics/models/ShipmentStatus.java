package za.charurama.logistics.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "shipment_consignment")
public class ShipmentStatus {
    @Id
    private String id;
    @Indexed
    private String manifestReference; //unique per shipment
    @Indexed
    private long wayBillNumber;
    @Indexed
    private String vehicleId;
    @Indexed
    private int statusId;
    private Date createdDate;
}
