package za.charurama.logistics.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "vehicle_location")
public class VehicleLocation  {
    @Id
    private String id;
    @Indexed
    private String deviceId;
    @Indexed
    private String vehicleId;
    private float latitude;
    private float longitude;
    private Date locationDateTime;

}
