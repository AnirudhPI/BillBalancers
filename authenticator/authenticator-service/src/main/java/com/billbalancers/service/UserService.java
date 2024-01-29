package com.billbalancers.service;

import com.billbalancers.model.User;
import com.billbalancers.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void insertData(String email,String password,String first_name,String last_name) {
        User entity = new User();
        entity.setEmail(email);
        entity.setFirstName(first_name);
        entity.setPassword(password);
        entity.setLastName(last_name);
        this.userRepository.save(entity);
    }
}
