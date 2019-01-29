package za.charurama.logistics.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "vehicle")
public class Vehicle {
    @Id
    private String id;
    private String make;
    private String model;
    private String licenseId;
    private int year;
}
