package za.charurama.logistics.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "auto_number")
public class AutoNumber {
    @Id
    private String id;
    @Indexed
    private String sequenceName;
    private long sequence;
    private int incrementBy;
}
