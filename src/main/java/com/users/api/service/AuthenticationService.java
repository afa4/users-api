package com.users.api.service;

import com.users.api.exception.ExpiredTokenException;
import com.users.api.exception.UnauthorizedException;
import com.users.api.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;
    @Value("${token.ttl}")
    private int tokenTtl;

    public void validateAuthorization(String authorization) throws UnauthorizedException, ExpiredTokenException {
        if (Objects.nonNull(authorization) && authorization.startsWith("Bearer ")) {
            try {
                String token = authorization.substring(7);
                var user = userService.findByToken(token);
                if (tokenExpired(user.getLastLogin())) {
                    throw new ExpiredTokenException();
                }
            } catch (UserNotFoundException e) {
                throw new UnauthorizedException();
            }
        } else {
            throw new UnauthorizedException();
        }
    }

    private boolean tokenExpired(Date userLastLogin) {
        return userLastLogin.getTime() - System.currentTimeMillis() > tokenTtl;
    }
}
