package za.charurama.logistics.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "system_admin")
public class SystemAdmin {
    @Id
    private String id;
    @Indexed
    private String userId;
    private Boolean isAdmin;

    public SystemAdmin(){

    }
    public SystemAdmin(String userId, boolean isAdmin) {
        this.userId = userId;
        this.isAdmin = isAdmin;
    }
}
