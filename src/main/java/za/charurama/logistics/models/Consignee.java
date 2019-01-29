package za.charurama.logistics.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "consignee")
public class Consignee {
    @Id
    private String id;
    private String name;
    private String address;
    private String address2;
    private String contactNumber;
    private String country;
}
