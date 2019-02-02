package za.charurama.logistics.models;

import lombok.Data;

@Data
public class RestResponse {
    private boolean isError;
    private String message;

    public RestResponse(String message){
        this.message = message;
    }

    public RestResponse(boolean isError, String message) {
        this.isError = isError;
        this.message = message;
    }
}
