package za.charurama.logistics.services;

import org.apache.commons.io.IOUtils;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.charurama.logistics.cache.RedisCache;
import za.charurama.logistics.cache.RedisCacheClient;
import za.charurama.logistics.common.LoginCipher;
import za.charurama.logistics.constants.MessagingTypes;
import za.charurama.logistics.models.*;
import za.charurama.logistics.repository.MessagingLogsRepository;
import za.charurama.logistics.repository.SystemAdminsRepository;
import za.charurama.logistics.repository.UserRepository;

import java.io.IOException;
import java.util.concurrent.Executors;

@Service
public class UserLoginService extends Notify {
    @Autowired
    MessagingLogsRepository messagingLogsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SendGridEmailService sendGridEmailService;
    @Autowired
    RealtimeService realtimeService;
    @Autowired
    SystemAdminsRepository systemAdminsRepository;

    private RedisCache cacheClient = RedisCacheClient.getInstance().getCache();

    public RestResponse saveUser(User model)  {
        boolean isNewUser = false;
        if (model.getId() == null || model.getId().isEmpty()) {
            model.setId(null);
            isNewUser= true;
            User user = getUser(model.getEmailAddress());
            if (user != null) {
                return new RestResponse(true,"Username already exist");
            }
        }
        String rawPassword = model.getPassword();
        model.setPassword(LoginCipher.encrypt(rawPassword));
        User result = userRepository.save(model);
        boolean finalIsNewUser = isNewUser;
        Executors.newSingleThreadExecutor().execute(() -> {
            NotifyAlways("User " + result.getFirstName() + " created");
            cacheClient.setItem(result.getEmailAddress(), result);
            //get html template
            String html = getHtml(result);
            if (finalIsNewUser)
                sendEmailNotification(result.getId(), result.getEmailAddress(), "Zimcon Login Details", html);
        });
        return new RestResponse(false,"User created successfully");
    }

    public RestResponse updateUser(User model){
        User user = getUser(model.getEmailAddress());
        if (user == null) {
            return new RestResponse(true,"Username does not exist");
        }
        //update password via change password
        userRepository.save(model);
        return new RestResponse(false,"User updated successfully");
    }

    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    public LoginResponse login(String email, String password) {
        User user = getUser(email);
        if (user != null) {
            String storedPassword = LoginCipher.decrpty(user.getPassword());
            //is admin
            SystemAdmin admin = checkIfAdmin(user.getId());
            return storedPassword.equalsIgnoreCase(password)
                    ? new LoginResponse(false, "", user, admin.getIsAdmin())
                    : new LoginResponse(true, "Username/Password combination is incorrect", null, admin.getIsAdmin());
        }
        return new LoginResponse(true, "User does not exist", null, false);
    }

    public User changePassword(String email,String password){
        User user1 = getUser(email);
        if ( user1 != null ){
            user1.setPassword(LoginCipher.encrypt(password));
            User response = userRepository.save(user1);
            cacheClient.setItem(response.getEmailAddress(),response);
            return user1;
        }
        return new User();
    }

    private User getUser(String userName){
        return cacheClient.getItem(userName,User.class);
    }

    private String getHtml(User user) {
        String result = "";
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream("static/welcome.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.replace("%full_name%", String.format("%s %s", user.getFirstName(), user.getLastName()))
                .replace("%user_name%", user.getEmailAddress())
                .replace("%user_password%", "123456789");
    }

    private String getHtml(User user,String password) {
        String result = "";
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream("static/recover.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.replace("%full_name%", String.format("%s %s", user.getFirstName(), user.getLastName()))
                .replace("%user_name%", user.getEmailAddress())
                .replace("%user_password%", password);
    }

    private void sendEmailNotification(String userId,String destination,String subject,String body) {
        NotifyAlways("New user email sent to " + destination);
        String response = sendGridEmailService.sendHTML("welcome@zimcon.co.za", destination, subject, body);
        logMessages(new MessagingLog(userId, destination, body, MessagingTypes.EMAIL, response));
    }

    public RestResponse addUserToAdminGroup(SystemAdmin model){
        SystemAdmin record = systemAdminsRepository.findFirstByUserIdEquals(model.getUserId());
        if ( record != null ){
            return new RestResponse(true, "User already an Admin");
        }else {
            systemAdminsRepository.save(model);
            return new RestResponse(false, "User added to Admin successfully");
        }
    }

    public RestResponse revokeAdmin(String userName){
        User user = getUser(userName);
        if (user != null) {
           SystemAdmin record = systemAdminsRepository.findFirstByUserIdEquals(user.getId());
           systemAdminsRepository.delete(record);
            return new RestResponse(false,"Admin rights have been revoked for this user");
        }
        return new RestResponse(true,"User does not exist");
    }

    public SystemAdmin checkIfAdmin(String userId){
        SystemAdmin systemAdmin = systemAdminsRepository.findFirstByUserIdEquals(userId);
        if (systemAdmin == null)
            return new SystemAdmin(userId,false);
        return new SystemAdmin(userId,true);
    }

    private void logMessages(MessagingLog messagingLogs){
        messagingLogsRepository.save(messagingLogs);
    }

    @Override
    public void NotifyAlways(String message) {
        try {
            realtimeService.sendMessage(message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public RestResponse recoverPassword(String email) {
        User user = getUser(email);
        if (user != null) {
            Executors.newSingleThreadExecutor().execute(() -> {
                NotifyAlways("User: " + user.getFirstName() + " recovered his password");
                //get html template
                String html = getHtml(user,LoginCipher.decrpty(user.getPassword()));
                sendEmailNotificationRecovery(user.getId(), user.getEmailAddress(), "Zimcon Login Details", html);
            });
        }else{
            return new RestResponse(true,"Username does not exist");
        }
        return new RestResponse(false,"An Email has been sent to your inbox with login credentials");
    }

    private void sendEmailNotificationRecovery(String userId, String destination, String subject, String body) {
        NotifyAlways("Recover email sent to " + destination);
        String response = sendGridEmailService.sendHTML("passwordrecovery@zimcon.co.za", destination, subject, body);
        logMessages(new MessagingLog(userId, destination, body, MessagingTypes.EMAIL, response));
    }
}
