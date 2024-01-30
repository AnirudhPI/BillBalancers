package com.billbalancers.service;

import com.billbalancers.authenticatorapi.model.User;
import com.billbalancers.model.UserData;
import com.billbalancers.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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
        UserData userData =  new UserData();
        userData.setEmail(user.getEmail());
        userData.setPassword(user.getPassword());
        userData.setFirstName(user.getFirstName());
        userData.setLastName(user.getLastName());
        this.userRepository.save(userData);
    }
}
