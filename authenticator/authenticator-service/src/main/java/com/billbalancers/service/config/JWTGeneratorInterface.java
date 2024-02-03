package com.billbalancers.service.config;

import com.billbalancers.authenticatorapi.model.UserLogin;

import java.util.Map;

public interface JWTGeneratorInterface {
    String generateToken(UserLogin userLogin);
    String parseJwt(String jwtString) throws Exception;
}
