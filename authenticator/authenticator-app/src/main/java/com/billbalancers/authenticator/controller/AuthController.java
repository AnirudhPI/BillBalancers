package com.billbalancers.authenticator.controller;


import com.billbalancers.authenticatorapi.api.AuthApi;
import com.billbalancers.authenticatorapi.model.Message;
import com.billbalancers.authenticatorapi.model.User;
import com.billbalancers.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan(basePackages = "com.billbalancers")
public class AuthController implements AuthApi {


    @Autowired
    private final AuthenticationService authService;

    public AuthController(AuthenticationService auth) {
        this.authService = auth;
    }

    @Override
    public ResponseEntity<Message> getAuth(User user){
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        Message message = new Message();
       message.setMessage(this.authService.signup(email,password,firstName,lastName).getMessage());
        return ResponseEntity.ok(message);

    }
}
