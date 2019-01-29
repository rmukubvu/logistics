package za.charurama.logistics.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "smart_device_allocation")
public class SmartDeviceAllocation {
    @Id
    private String id;
    @Indexed
    private String deviceId;
    private String vehicleId;
    private Date allocationDate;
}
