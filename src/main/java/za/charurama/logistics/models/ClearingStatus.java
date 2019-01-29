package za.charurama.logistics.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "clearing_status")
public class ClearingStatus {
    @Id
    private String id;
    private String status;
    private int statusId;
}
