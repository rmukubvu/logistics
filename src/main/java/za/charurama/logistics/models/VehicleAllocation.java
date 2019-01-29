package za.charurama.logistics.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "vehicle_allocation")
public class VehicleAllocation {
    @Id
    private String id;
    @Indexed
    private String vehicleId;
    private String driverId;
    private Date allocatedStartDate;
    @Indexed
    private Date allocatedEndDate;
}
