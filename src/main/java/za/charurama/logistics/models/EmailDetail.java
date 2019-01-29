package za.charurama.logistics.models;

import lombok.Data;

@Data
public class EmailDetail {
    private Iterable<String> to;
    private String content;
    private String subject;

    public EmailDetail(Iterable<String> to, String content){
        this.to = to;
        this.content = content;
    }

    public EmailDetail(Iterable<String> to, String content, String additionalInformation) {
        this.to = to;
        this.content = content;
        this.subject = additionalInformation;
    }

}
