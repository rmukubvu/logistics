package za.charurama.logistics.models;


import lombok.Data;

@Data
public class LoginResponse {
    private boolean isError;
    private String loginErrorMessage;
    private User user;

    public LoginResponse() {
    }

    public LoginResponse(boolean isError, String loginErrorMessage, User user) {
        this.isError = isError;
        this.loginErrorMessage = loginErrorMessage;
        this.user = user;
    }
}
