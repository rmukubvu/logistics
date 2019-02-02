package za.charurama.logistics.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "user")
public class User {
    @Id
    private String id;
    @Indexed
    private String consigneeId;
    @Indexed
    private String emailAddress;
    private String password;
    private String firstName;
    private String lastName;
    private Date createdDate;
}
