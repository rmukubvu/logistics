package za.charurama.logistics.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "messaging_logs")
public class MessagingLog {
    @Id
    private String id;
    private String vehicleId;
    private String telephone;
    private String message;
    private String messageType;
    private String messageResponse;
    private Date createdDate;

    public MessagingLog(String vehicleId, String telephone, String message, String messageType, String messageResponse) {
        this.vehicleId = vehicleId;
        this.telephone = telephone;
        this.message = message;
        this.messageType = messageType;
        this.messageResponse = messageResponse;
        this.createdDate = new Date();
    }

}
