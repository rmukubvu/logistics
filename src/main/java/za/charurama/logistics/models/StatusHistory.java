package za.charurama.logistics.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(value = "status_change")
public class StatusHistory {
    @Id
    private String id;
    @Indexed
    private long wayBillNumber;
    private String status;
    private Date statusChangeDate;
}
