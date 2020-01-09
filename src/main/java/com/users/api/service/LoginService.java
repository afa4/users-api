package com.users.api.service;

import com.users.api.exception.EmailOrPasswordIncorrectException;
import com.users.api.model.dto.UserCreatedDTO;
import com.users.api.repository.UserRepository;
import com.users.api.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    public UserCreatedDTO login(String email, String password) throws EmailOrPasswordIncorrectException {
        var user = userRepository.findByEmailAndPassword(email, PasswordUtil.encrypt(password))
                .orElseThrow(EmailOrPasswordIncorrectException::new);
        user.setLastLogin(new Date());
        userRepository.save(user);
        return new UserCreatedDTO(
                user.getUuid(),
                user.getCreated(),
                user.getModified(),
                user.getLastLogin(),
                user.getToken()
        );
    }


}
