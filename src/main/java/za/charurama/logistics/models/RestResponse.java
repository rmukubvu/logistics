package za.charurama.logistics.models;

import lombok.Data;

@Data
public class RestResponse {
    private String message;
    public RestResponse(String message){
        this.message = message;
    }
}
