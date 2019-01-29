package za.charurama.logistics.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "client_contact_details")
public class ClientContactDetails {
    @Id
    private String id;
    private String consigneeId;
    private String mobileNumber;
}
