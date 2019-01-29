package za.charurama.logistics.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "shipment_movement")
public class ShipmentMovement {
    @Id
    private String id;
    @Indexed
    private String manifestReference;
    @Indexed
    private long wayBillNumber;
    private double latitude;
    private double longitude;
    private Date createdDate;
}
