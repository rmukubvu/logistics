package za.charurama.logistics.models;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "consignee_contact_details")
public class ConsigneeContactDetails {
    @Id
    private String id;
    @Indexed
    private String consigneeId;
    private String name;
    @Indexed
    private String telephone;
    private String emailAddress;
    private String countryCode;

}
