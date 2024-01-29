package com.billbalancers.service;

import com.billbalancers.service.pojos.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserService userService;

    @Autowired
    public AuthenticationService(UserService userService) {
        this.userService = userService;
    }

    public Message signup(String email,String password,String firstName,String lastName){

        this.userService.insertData(email,password,firstName,lastName);
        return new Message("Sign In Successful");
    }


}