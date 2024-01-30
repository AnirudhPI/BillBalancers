package com.billbalancers.service;


import com.billbalancers.service.pojos.Message;
import com.billbalancers.authenticatorapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserService userService;

    @Autowired
    public AuthenticationService(UserService userService) {
        this.userService = userService;
    }

    public Message signup(User user){

        this.userService.insertData(user);
        return new Message("Sign In Successful");
    }


}