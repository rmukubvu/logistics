package za.charurama.logistics.models;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ClickatellSmsMessageResponse {

    @JsonProperty("apiMessageId")
    private String apiMessageId;
    @JsonProperty("accepted")
    private Boolean accepted;
    @JsonProperty("to")
    private String to;
    @JsonProperty("errorCode")
    private Object errorCode;
    @JsonProperty("error")
    private Object error;
    @JsonProperty("errorDescription")
    private Object errorDescription;

    @JsonProperty("apiMessageId")
    public String getApiMessageId() {
        return apiMessageId;
    }

    @JsonProperty("apiMessageId")
    public void setApiMessageId(String apiMessageId) {
        this.apiMessageId = apiMessageId;
    }

    @JsonProperty("accepted")
    public Boolean getAccepted() {
        return accepted;
    }

    @JsonProperty("accepted")
    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @JsonProperty("to")
    public String getTo() {
        return to;
    }

    @JsonProperty("to")
    public void setTo(String to) {
        this.to = to;
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