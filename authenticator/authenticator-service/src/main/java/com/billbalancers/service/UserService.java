package com.billbalancers.service;

import com.billbalancers.authenticatorapi.model.User;
import com.billbalancers.authenticatorapi.model.UserLogin;
import com.billbalancers.model.UserData;
import com.billbalancers.model.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.Optional;

@Service
@Transactional
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
        String password = userLogin.getPassword();
        boolean userExists = userRepository.existsUserByEmail(userLogin.getEmail());
        if(!userExists){
            throw new DataIntegrityViolationException("No user found");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(!bCryptPasswordEncoder.matches(password,userRepository.findPasswordByEmail(email).getPassword())) {
            throw new RestClientException("Wrong Password");
        }

    }
    public UserData getUserData(String email){
        System.out.println(this.userRepository.findUserDataByEmail(email).getFirstName());
        return this.userRepository.findUserDataByEmail(email);
    }

    public void updateUserData(User user) {
        UserData userToBeUpdated = this.userRepository.findByEmail(user.getEmail());

        userToBeUpdated.setFirstName(user.getFirstName());
        userToBeUpdated.setLastName(user.getLastName());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        userToBeUpdated.setPassword(encodedPassword);
        System.out.println(user.getFirstName());
        this.userRepository.save(userToBeUpdated);

    }

}
