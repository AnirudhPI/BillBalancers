package com.billbalancers.authenticator.controller;


import com.billbalancers.authenticatorapi.api.AuthApi;
import com.billbalancers.authenticatorapi.model.*;
import com.billbalancers.service.AuthenticationService;
import com.billbalancers.service.JWTGeneratorService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

@RestController
@ComponentScan(basePackages = "com.billbalancers")
public class AuthController implements AuthApi{


    @Autowired
    private final AuthenticationService authService;
    private final JWTGeneratorService jwtGeneratorService;

    public AuthController(AuthenticationService auth, JWTGeneratorService jwtGeneratorService) {
        this.authService = auth;
        this.jwtGeneratorService = jwtGeneratorService;
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
    public ResponseEntity<MessageWithToken> getAuthLogin(UserLogin userLogin, @RequestHeader(required = false) String jwtToken) {
        try {
            MessageWithToken message = new MessageWithToken();
            if(jwtToken != null){
                this.jwtGeneratorService.parseJwt(jwtToken);
            }
            message.setMessage(this.authService.login(userLogin).getMessage());
            message.setToken(this.jwtGeneratorService.generateToken(userLogin));
            return ResponseEntity.ok(message);
        }
        catch(DataIntegrityViolationException e){
            MessageWithToken m = new MessageWithToken();
            m.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(m);
        }
        catch(RestClientException | ExpiredJwtException e){
            MessageWithToken m = new MessageWithToken();
            m.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(m);
        }
    }

    @Override
    public ResponseEntity<ProfileData> getProfileDetails(String jwtToken) {
        ProfileData profileData = new ProfileData();
        String email = this.jwtGeneratorService.parseJwt(jwtToken);
        profileData.setEmail(email);
        profileData.setFirstName(this.authService.getUserData(email).getFirstName());
        profileData.setLastName(this.authService.getUserData(email).getLastName());
        return ResponseEntity.ok(profileData);

    }
}
