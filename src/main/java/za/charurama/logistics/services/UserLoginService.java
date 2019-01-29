package za.charurama.logistics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.charurama.logistics.common.LoginCipher;
import za.charurama.logistics.models.LoginResponse;
import za.charurama.logistics.models.User;
import za.charurama.logistics.repository.UserRepository;

@Service
public class UserLoginService {
    @Autowired
    UserRepository userRepository;

    public User saveUser(User user){
        user.setPassword(LoginCipher.encrypt(user.getPassword()));
        return userRepository.save(user);
    }

    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    public LoginResponse login(String email, String password){
        User user = userRepository.findDistinctByEmailAddressEquals(email);
        if ( user != null ){
            String storedPassword = LoginCipher.decrpty(user.getPassword());
            if (storedPassword.equalsIgnoreCase(password)){
                return new LoginResponse(false,"",user);
            }else{
                return new LoginResponse(true,"Username/Password combination is incorrect",user);
            }
        }
        return new LoginResponse(true,"User does not exist",null);
    }

    public User changePassword(String email,String password){
        User user1 = userRepository.findDistinctByEmailAddressEquals(email);
        if ( user1 != null ){
            user1.setPassword(LoginCipher.encrypt(password));
            userRepository.save(user1);
            return user1;
        }
        return new User();
    }
}
