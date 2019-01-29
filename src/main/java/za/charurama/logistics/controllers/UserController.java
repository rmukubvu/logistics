package za.charurama.logistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import za.charurama.logistics.models.LoginResponse;
import za.charurama.logistics.models.User;
import za.charurama.logistics.services.UserLoginService;

@RestController
public class UserController {
    @Autowired
    UserLoginService userLoginService;

    @GetMapping(value = "/login" , produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse login(String email, String password){
        return userLoginService.login(email,password);
    }

    @GetMapping(value = "/users" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<User> getAllUsers(){
        return userLoginService.getAllUsers();
    }

    @PostMapping(value = "/user",produces = MediaType.APPLICATION_JSON_VALUE)
    public User saveUser(@RequestBody User user){
        return userLoginService.saveUser(user);
    }

    @GetMapping(value = "/changePassword",produces = MediaType.APPLICATION_JSON_VALUE)
    public User changePassword(@RequestParam(name = "email") String email,@RequestParam(name = "password") String password){
        return userLoginService.changePassword(email, password);
    }

}
