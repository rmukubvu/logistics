package za.charurama.logistics.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "driver")
public class Driver {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String nationalId;
    private String passportNumber;
    private String telephone;
    private String country;
}
