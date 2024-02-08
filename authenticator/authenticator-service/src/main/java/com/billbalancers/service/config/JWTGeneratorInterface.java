package com.billbalancers.service.config;

import com.billbalancers.authenticatorapi.model.UserLogin;
import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JWTGeneratorInterface {
    String generateToken(UserLogin userLogin);
    Claims parseJwt(String jwtString) throws Exception;
}
