package com.billbalancers.authenticator.controller;


import com.billbalancers.authenticatorapi.api.AuthApi;
import com.billbalancers.authenticatorapi.model.Message;
import com.billbalancers.authenticatorapi.model.User;
import com.billbalancers.authenticatorapi.model.UserLogin;
import com.billbalancers.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

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
        try {
            Message message = new Message();
            message.setMessage(this.authService.signup(user).getMessage());
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
        catch(Exception e){
            Message m = new Message();
            m.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(m);
        }

    }

    @Override
    public ResponseEntity<Message> getAuthLogin(UserLogin userLogin) {
        try {
            Message message = new Message();
            message.setMessage(this.authService.login(userLogin).getMessage());
            return ResponseEntity.ok(message);
        }
        catch(DataIntegrityViolationException e){
            Message m = new Message();
            m.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(m);
        }
        catch(RestClientException e){
            Message m = new Message();
            m.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(m);
        }
    }
}
