package za.charurama.logistics.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "smart_device")
public class SmartDevice {
    @Id
    private String id;
    private String deviceId;
    private String model;
    private String make;
    private String serialNumber;

}
