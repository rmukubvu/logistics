package za.charurama.logistics.services;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.charurama.logistics.cache.RedisCache;
import za.charurama.logistics.cache.RedisCacheClient;
import za.charurama.logistics.common.LoginCipher;
import za.charurama.logistics.constants.MessagingTypes;
import za.charurama.logistics.models.LoginResponse;
import za.charurama.logistics.models.MessagingLog;
import za.charurama.logistics.models.RestResponse;
import za.charurama.logistics.models.User;
import za.charurama.logistics.repository.MessagingLogsRepository;
import za.charurama.logistics.repository.UserRepository;

import java.io.IOException;
import java.util.concurrent.Executors;

@Service
public class UserLoginService {
    @Autowired
    MessagingLogsRepository messagingLogsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SendGridEmailService sendGridEmailService;

    private RedisCache cacheClient = RedisCacheClient.getInstance().getCache();

    public RestResponse saveUser(User model)  {
        boolean isNewUser = false;
        if (model.getId() == null || model.getId().isEmpty()) {
            model.setId(null);
            isNewUser= true;
            User user = getUser(model.getEmailAddress());
            if (user != null) {
                return new RestResponse(true,"Username already exists");
            }
        }
        String rawPassword = model.getPassword();
        model.setPassword(LoginCipher.encrypt(rawPassword));
        User result = userRepository.save(model);
        boolean finalIsNewUser = isNewUser;
        Executors.newSingleThreadExecutor().execute(() -> {
            cacheClient.setItem(result.getEmailAddress(), result);
            //get html template
            String html = getHtml(result);
            if (finalIsNewUser)
                sendEmailNotification(result.getId(), result.getEmailAddress(), "Zimcon Login Details", html);
        });
        return new RestResponse(false,"User created successfully");
    }

    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    public LoginResponse login(String email, String password){
        User user = getUser(email);
        if ( user != null ){
            String storedPassword = LoginCipher.decrpty(user.getPassword());
            return storedPassword.equalsIgnoreCase(password)
                    ? new LoginResponse(false, "", user)
                    : new LoginResponse(true, "Username/Password combination is incorrect", user);
        }
        return new LoginResponse(true,"User does not exist",null);
    }

    public User changePassword(String email,String password){
        User user1 = userRepository.findDistinctByEmailAddressEquals(email);
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

    private void sendEmailNotification(String userId,String destination,String subject,String body) {
        String response = sendGridEmailService.sendHTML("welcome@zimcon.co.za", destination, subject, body);
        logMessages(new MessagingLog(userId, destination, body, MessagingTypes.EMAIL, response));
    }

    private void logMessages(MessagingLog messagingLogs){
        messagingLogsRepository.save(messagingLogs);
    }
}
