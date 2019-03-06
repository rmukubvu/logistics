package za.charurama.logistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import za.charurama.logistics.exceptions.UserExistsException;
import za.charurama.logistics.models.*;
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

    @GetMapping(value = "/usersWithAdminStatus" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<UserViewModel> getAllUsersWithAdmin(){
        return userLoginService.getAllViewModelList();
    }

    @PostMapping(value = "/user",produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse saveUser(@RequestBody User user) {
        return userLoginService.saveUser(user);
    }

    @PostMapping(value = "/userAdmin",produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse saveUserAdmin(@RequestBody SystemAdmin systemAdmin) {
        return userLoginService.addUserToAdminGroup(systemAdmin);
    }

    @PostMapping(value = "/user/update",produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse updateUser(@RequestBody User user) {
        return userLoginService.updateUser(user);
    }

    @GetMapping(value = "/changePassword",produces = MediaType.APPLICATION_JSON_VALUE)
    public User changePassword(@RequestParam(name = "email") String email,@RequestParam(name = "password") String password){
        return userLoginService.changePassword(email, password);
    }

    @GetMapping(value = "/revokeAdmin",produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse revokeAdmin(@RequestParam(name = "email") String email){
        return userLoginService.revokeAdmin(email);
    }

    @GetMapping(value = "/recoverPassword",produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse recoverPassword(@RequestParam(name = "email") String email){
        return userLoginService.recoverPassword(email);
    }

    @DeleteMapping(value = "/removeUser",produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse removeUser(@RequestParam(name = "email") String email){
        return userLoginService.removeUser(email);
    }

}
