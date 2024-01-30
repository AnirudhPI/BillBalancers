package com.billbalancers.service;

import com.billbalancers.authenticatorapi.model.User;
import com.billbalancers.authenticatorapi.model.UserLogin;
import com.billbalancers.model.UserData;
import com.billbalancers.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
public class UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void insertData(User user) {

        boolean userExists = userRepository.existsUserByEmail(user.getEmail());
        if(userExists){
            throw new DataIntegrityViolationException("User exists with this email");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        UserData userData =  new UserData();
        userData.setEmail(user.getEmail());
        userData.setPassword(encodedPassword);
        userData.setFirstName(user.getFirstName());
        userData.setLastName(user.getLastName());
        this.userRepository.save(userData);
    }

    public void loginValidation(UserLogin userLogin) {

        String email = userLogin.getEmail();
        // String password = user.getPassword();

        boolean userExists = userRepository.existsUserByEmail(userLogin.getEmail());
        if(!userExists){
            throw new DataIntegrityViolationException("No user found");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(userLogin.getPassword());

        if(!encodedPassword.equals(userRepository.findPasswordByEmail(email))) {
            throw new RestClientException("Wrong Password");
        }

    }


}
