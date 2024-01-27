package com.billbalancers.authenticator.controller;

import com.billbalancers.authenticatorapi.api.AuthApi;
import com.billbalancers.authenticatorapi.model.Message;
import com.billbalancers.authenticatorapi.model.User;
import org.springframework.http.ResponseEntity;

public class AuthController implements AuthApi {

    @Override
    public ResponseEntity<Message> getAuth(User user){
        return null;
    }
}
