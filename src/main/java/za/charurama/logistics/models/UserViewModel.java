package za.charurama.logistics.models;

import lombok.Data;

@Data
public class UserViewModel {
    private User user;
    private String isAdmin;

    public UserViewModel(User user,boolean admin){
        this.user = user;
        this.isAdmin = admin == true ? "YES" : "NO";
    }
}
