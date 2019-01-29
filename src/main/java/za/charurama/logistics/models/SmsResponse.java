package za.charurama.logistics.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "clickatell_sms_response")
public class SmsResponse {
    @Id
    private String id;
    @JsonProperty("messages")
    private List<ClickatellSmsMessageResponse> messages = null;
    @JsonProperty("errorCode")
    private Object errorCode;
    @JsonProperty("error")
    private Object error;
    @JsonProperty("errorDescription")
    private Object errorDescription;

    @JsonProperty("messages")
    public List<ClickatellSmsMessageResponse> getMessages() {
        return messages;
    }

    @JsonProperty("messages")
    public void setMessages(List<ClickatellSmsMessageResponse> messages) {
        this.messages = messages;
    }

    @JsonProperty("errorCode")
    public Object getErrorCode() {
        return errorCode;
    }

    @JsonProperty("errorCode")
    public void setErrorCode(Object errorCode) {
        this.errorCode = errorCode;
    }

    @JsonProperty("error")
    public Object getError() {
        return error;
    }

    @JsonProperty("error")
    public void setError(Object error) {
        this.error = error;
    }

    @JsonProperty("errorDescription")
    public Object getErrorDescription() {
        return errorDescription;
    }

    @JsonProperty("errorDescription")
    public void setErrorDescription(Object errorDescription) {
        this.errorDescription = errorDescription;
    }
}
