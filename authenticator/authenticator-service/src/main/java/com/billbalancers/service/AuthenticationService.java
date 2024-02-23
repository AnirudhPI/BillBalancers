package com.billbalancers.service;


import com.billbalancers.authenticatorapi.model.UserLogin;
import com.billbalancers.model.UserData;
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
        return new Message("Sign Up Successful");
    }

    public Message login(UserLogin userLogin) {

        Long ID = this.userService.loginValidation(userLogin);
        return new Message("Login Successful",ID);
    }

    public UserData getUserData(String email){
        return this.userService.getUserData(email);
    }

    public Message updateUserData(User user){
        this.userService.updateUserData(user);
        return new Message("Update Successful");
    }
    public Message logoutUser(String jwtToken){
        this.userService.logout(jwtToken);
        return new Message("Logout Successful");
    }


}